package tests;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest extends BaseTest {
    private Courier courier;
    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.withLogin(RandomStringUtils.randomAlphabetic(12))
                .withPassword(RandomStringUtils.randomAlphabetic(10))
                .withFirstName(RandomStringUtils.randomAlphabetic(11));

        courierSteps.createCourier(courier);
    }

    @Test
    @DisplayName("Тест.Логин курьера")
    @Description("Проверка успешной авторизации курьера с валидными учетными данными")
    public void shouldLoginCourierTest() {
        courierSteps.loginCourier(courier)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Тест.Логин курьера без поля логин")
    @Description("Проверка ошибки авторизации при попытке входа без указания логина")
    public void shouldLoginCourierWithoutLoginTest() {
        courier.withLogin(null);
        courierSteps.loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест.Логин курьера без поля пароль")
    @Description("Проверка ошибки авторизации при попытке входа без указания пароля")
    public void shouldLoginCourierWithoutPasswordTest() {
        courier.withPassword(null);
        courierSteps.loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест.Логин несуществующего курьера")
    @Description("Проверка ошибки авторизации при попытке входа с несуществующим логином")
    public void shouldLoginCourierWithoutCreateCourierTest() {
        courier.withLogin(RandomStringUtils.randomAlphabetic(12));
        courierSteps.loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест.Логин курьера с неправильным паролем")
    @Description("Проверка ошибки авторизации при попытке входа с неверным паролем")
    public void shouldLoginCourierWithWrongPasswordTest() {
        courier.withPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(courier).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(new Courier().withId(id));
        }
    }
}
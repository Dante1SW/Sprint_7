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

import static org.apache.http.HttpStatus.*;

public class CourierCreateTest extends BaseTest {
    private Courier courier;
    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.withLogin(RandomStringUtils.randomAlphabetic(12))
                .withPassword(RandomStringUtils.randomAlphabetic(10))
                .withFirstName(RandomStringUtils.randomAlphabetic(11));
    }

    @Test
    @DisplayName("Тест.Успешное создание курьера")
    @Description("Проверка успешного создания нового курьера с валидными данными")
    public void shouldCreateCourierTest() {
        courierSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест.Нельзя создать двух курьеров с одинаковым логином")
    @Description("Проверка, что система не позволяет создать курьера с уже существующим логином")
    public void shouldNotAllowDuplicateCourierLoginTest() {
        courierSteps.createCourier(courier).statusCode(SC_CREATED);
        Courier duplicate = new Courier()
                .withLogin(courier.getLogin())
                .withPassword("different_password")
                .withFirstName("Different Name");
        courierSteps.createCourier(duplicate)
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Тест.Нельзя создать курьера без поля логин")
    @Description("Проверка, что система возвращает ошибку при попытке создать курьера без указания логина")
    public void shouldNotCreateCourierWithoutLoginTest() {
        courier.withLogin(null);
        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест.Нельзя создать курьера без поля пароль")
    @Description("Проверка, что система возвращает ошибку при попытке создать курьера без указания пароля")
    public void shouldNotCreateCourierWithoutPasswordTest() {
        courier.withPassword(null);
        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(courier).extract().path("id");
        if (id != null) {
            courierSteps.deleteCourier(new Courier().withId(id));
        }
    }
}
package tests;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import models.Orders;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrdersSteps;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    private final String[] colorInput;
    private final String testDescription;
    private Orders order;
    private Integer track;
    private final OrdersSteps ordersSteps = new OrdersSteps();

    public CreateOrderTest(String[] colorInput, String testDescription) {
        this.colorInput = colorInput;
        this.testDescription = testDescription;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {1}")
    @Description("Параметризованные данные для тестирования создания заказов с различными цветами самокатов")
    public static Object[][] getData() {
        return new Object[][]{
                {new String[]{"BLACK"}, "Черный самокат"},
                {new String[]{"GREY"}, "Серый самокат"},
                {new String[]{"BLACK", "GREY"}, "Два цвета: черный и серый"},
                {new String[]{""}, "Без указания цвета"},
        };
    }

    @Before
    @Description("Подготовка тестовых данных: создание случайного заказа с настройкой цвета согласно параметрам теста")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        order = new Orders()
                .withFirstNameClient(RandomStringUtils.randomAlphabetic(8))
                .withLastName(RandomStringUtils.randomAlphabetic(10))
                .withAddress("г. Москва, ул. " + RandomStringUtils.randomAlphabetic(7))
                .withMetroStation("4")
                .withPhone("+7" + RandomStringUtils.randomNumeric(10))
                .withRentTime(5)
                .withDeliveryDate("2025-12-15")
                .withComment(RandomStringUtils.randomAlphabetic(20));

        if (colorInput != null && colorInput.length > 0 && !colorInput[0].isEmpty()) {
            order.withColor(colorInput);
        }
    }

    @Test
    @DisplayName("Тест.Создание заказа с разными вариантами цвета")
    @Description("Параметризованный тест проверки создания заказа с различными комбинациями цветов самоката")
    public void shouldCreateOrderWithColorTest() {
        track = ordersSteps.createOrders(order)
                .extract()
                .path("track");

        ordersSteps.createOrders(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }

    @After
    @Description("Очистка после теста: отмена созданного заказа по трек-номеру")
    public void tearDown() {
        if (track != null) {
            ordersSteps.cancelOrder(track);
        }
    }
}
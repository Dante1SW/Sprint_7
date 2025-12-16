package tests;

import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.Test;
import steps.OrderListSteps;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class OrderListTest extends BaseTest {

    @Before
    @Description("Настройка базовой конфигурации RestAssured: включение логирования запросов и ответов")
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Тест. Получение списка заказов")
    @Description("Проверка успешного получения списка всех заказов из системы. Убеждаемся, что ответ содержит корректную структуру с массивом заказов")
    public void shouldReturnOrderListTest() {
        OrderListSteps.getOrderList()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
package steps;

import io.qameta.allure.Step;

import io.restassured.response.ValidatableResponse;
import models.Orders;

import static constant.RestConfig.ORDERS;
import static constant.RestConfig.ORDERS_CANCEL;
import static io.restassured.RestAssured.given;

public class OrdersSteps {
    @Step("Создание заказа")
    public ValidatableResponse createOrders(Orders orders) {
        return given()
                .body(orders)
                .when()
                .post(ORDERS)
                .then();
    }

    @Step("Отмена заказа по трек-номеру")
    public ValidatableResponse cancelOrder(Integer trackNumber) {
        return given()
                .queryParam("track", trackNumber)
                .when()
                .put(ORDERS_CANCEL)
                .then();
    }
}
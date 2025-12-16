package steps;

import io.qameta.allure.Step;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

import static constant.RestConfig.ORDERS_LIST;
import static io.restassured.RestAssured.given;

public class OrderListSteps {
    @Step("Получение списка заказов")
    public static ValidatableResponse getOrderList() {

        return given()
                .when()
                .get(ORDERS_LIST)
                .then();
    }

    public static ResponseBody getOrderListBody() {

        return  given()
                .when()
                .get(ORDERS_LIST).body();

    }
}
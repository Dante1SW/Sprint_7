package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Courier;

import static constant.RestConfig.COURIER;
import static constant.RestConfig.COURIER_LOGIN;
import static io.restassured.RestAssured.given;
public class CourierSteps {
    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(Courier courier) {
        return given()
                .pathParams("id", courier.id)
                .when()
                .delete(COURIER + "/ {id}")
                .then();
    }
}
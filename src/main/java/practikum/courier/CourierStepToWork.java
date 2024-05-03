package practikum.courier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static practikum.Constants.*;

public class CourierStepToWork {
    public static RequestSpecification requestSpec() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("регистрация нового курьера")
    public ValidatableResponse createCourier(CourierData courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_CREATE)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse courierAuthorization(CourierLogin courierInfo) {
        return requestSpec()
                .body(courierInfo)
                .when()
                .post(POST_COURIER_LOGIN)
                .then();
    }

    @Step("удаление курьера")
    public ValidatableResponse courierDelete(int courierId) {
        return requestSpec()
                .when()
                .delete(DELETE_COURIER + courierId)
                .then();
    }

}
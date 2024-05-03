package practikum.order;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static practikum.Constants.*;

public class OrderStep {
    public static RequestSpecification requestSpecification() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Создание заказа")
    public ValidatableResponse successOrderCreate(OrderInfo orderInfo) {
        return requestSpecification()
                .when()
                .post(POST_ORDER_CREATE)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancellationOrder(int track) {
        return requestSpecification()
                .body(track)
                .when()
                .put(CANCEL_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return requestSpecification()
                .when()
                .get(GET_ORDERS_LIST)
                .then();
    }
}

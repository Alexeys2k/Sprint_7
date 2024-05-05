
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practikum.courier.*;


public class TestCourierLogin {
    int courierId;
    protected CourierGenerate courierGenerateRandomData = new CourierGenerate();
    private CourierData courierInfo;
    protected CourierStepToWork courierSteps;
    private CourierCreate courierAssertVoid;
    private CourierLogin courierLoginCredintals;

    @Before
    @Step("Создание тестовых данных для курьера")
    public void setUp() {
        courierSteps = new CourierStepToWork();
        courierInfo = courierGenerateRandomData.createCourierWithRandomData();
        courierSteps.createCourier(courierInfo);
        courierLoginCredintals = CourierLogin.from(courierInfo);
        courierAssertVoid = new CourierCreate();
    }

    @After
    @Step("Удаление ранее созданного курьера")
    public void cleanData() {
        courierSteps.courierDelete(courierId);
    }

    @DisplayName("Тест на успешное создание курьера")
    @Description("Логин с валидными данными")
    @Test
    public void testSuccessCourierLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(courierLoginCredintals);
        courierId = courierLogin.extract().path("id");
        courierAssertVoid.successLoginCourierAndTakeId(courierLogin);
    }

    @DisplayName("Тест на ошибку логина курьера из-за отсутсвия логина и пароля")
    @Description("Логин с пустыми данными")
    @Test
    public void testErrorCourierLoginWithEmptyCreds() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierLogin("", ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку логина курьера из-за несуществующей пары логин+пароль")
    @Description("Логин с не существующими данными")
    @Test
    public void testErrorCourierLoginWithDoesNotExistCredintals() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierLogin("f", "s"));
        courierAssertVoid.errorLoginCourierWithNotValidCredintals(courierLogin);
    }

    @DisplayName("Тест на ошибку логина курьера из-за пустого логина")
    @Description("Логин с пустым логином")
    @Test
    public void testErrorLoginCourierWithEmptyLogin() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierLogin("", courierInfo.getPassword()));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }

    @DisplayName("Тест на ошибку логина курьера из-за пустого пароля")
    @Description("Логин с пустым паролем")
    @Test
    public void testErrorLoginCourierWithEmptyPassword() {
        ValidatableResponse courierLogin = courierSteps.courierAuthorization(new CourierLogin(courierInfo.getLogin(), ""));
        courierAssertVoid.errorLoginCourierWithoutCredentials(courierLogin);
    }


}
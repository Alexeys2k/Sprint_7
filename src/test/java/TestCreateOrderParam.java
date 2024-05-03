import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import practikum.order.OrderInfo;
import practikum.order.OrderStep;


import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class TestCreateOrderParam {
    private final List<String> colour;
    private OrderStep orderSteps;
    int track;

    public TestCreateOrderParam(List<String> colour) {
        this.colour = colour;
    }
    @Before
    public void setUp(){
        orderSteps = new OrderStep();
    }

    @After
    public void cleanUp(){
        orderSteps.cancellationOrder(track);
    }

    @Parameterized.Parameters
    public static Object[][] selectScooterColour(){
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK","GREY")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @DisplayName("Успешное создание нового заказа")
    @Description("Создаем заказ с разными цветами")
    @Test
    public void createOrderWithDiffColours(){
        OrderInfo orderInfo = new OrderInfo(colour);
        ValidatableResponse response = orderSteps.successOrderCreate(orderInfo);
        track = response.extract().path("track");
        response.assertThat().statusCode(201).body("track", is(notNullValue()));
    }
}
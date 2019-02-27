package integrationtests;

import org.junit.Before;
import org.junit.Test;
import translation.domain.ShoppingCart;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ShoppingCartIT {

    private final ShoppingCart cart;

    public ShoppingCartIT() {
        this.cart = new ShoppingCart();
    }

    @Before
    public void setUp() {
        cart.emptyCart();
    }
    @Test
    public void canGetTotalAndDiscountFromTheService() {
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);
        cart.addItem("Cheese", BigDecimal.valueOf(4), 3);

        given().body(cart.toJson())
                .when().post("http://192.168.206.128:4567/shoppingCart/total")
                .then()
                .statusCode(200)
                .body(equalTo("$20.89"));

        given().body(cart.toJson())
                .when().post("http://192.168.206.128:4567/shoppingCart/totalWithDiscount")
                .then()
                .statusCode(200)
                .body(equalTo("$20.77"));
    }

}

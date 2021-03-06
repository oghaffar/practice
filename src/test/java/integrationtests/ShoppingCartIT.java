package integrationtests;

import org.junit.Before;
import org.junit.Test;
import translation.domain.ShoppingCart;
import translation.repository.ShoppingCartRepository;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ShoppingCartIT {

    public static final String serverAddress = "http://192.168.206.128:4567";
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
                .when().post(serverAddress + "/shoppingCart/total")
                .then()
                .statusCode(200)
                .body(equalTo("$20.89"));

        given().body(cart.toJson())
                .when().post(serverAddress + "/shoppingCart/totalWithDiscount")
                .then()
                .statusCode(200)
                .body(equalTo("$20.77"));
    }

    @Test
    public void canHandleInvalidJson() {
        given().body("Invalid Json")
                .when().post(serverAddress + "/shoppingCart/total")
                .then()
                .statusCode(200)
                .body(equalTo("Invalid request!"));

        given().body("Invalid Json")
                .when().post(serverAddress + "/shoppingCart/totalWithDiscount")
                .then()
                .statusCode(200)
                .body(equalTo("Invalid request!"));
    }

    @Test
    public void canRetrieveCartUsingSessionId() {
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);

        String sessionId = new ShoppingCartRepository().persistShoppingCart(cart);

        given()
//            .filter(RequestLoggingFilter.logRequestTo(System.out))
            .pathParam("sessionId", sessionId)
        .when()
            .get(serverAddress + "/shoppingCart/{sessionId}", sessionId)
        .then()
                .statusCode(200)
            .body(equalTo("{\"items\":[{\"productName\":\"Bread\",\"price\":3.45,\"quantity\":2}]}"));
    }
}

package translation.repository;

import org.junit.Test;
import translation.domain.ShoppingCart;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ShoppingCartRepositoryIT {

    @Test
    public void postgressConnectionSpike() throws Exception {
        Connection c = DriverManager.getConnection("jdbc:postgresql://192.168.206.128:5432/postgres","oghaffar", "notASecret");
        assertFalse(c.isClosed());
    }

    @Test
    public void canPersistAndRetrieveShoppingCart() {
        ShoppingCartRepository repository = new ShoppingCartRepository();

        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);
        cart.addItem("Cheese", BigDecimal.valueOf(4), 3);
        cart.addItem("Loyalty Gift", BigDecimal.ZERO, 1);

        String sessionId = repository.persistShoppingCart(cart);

        ShoppingCart retrievedCart = repository.retrieveShoppingCart(sessionId);
        assertEquals(new BigDecimal("20.89"), retrievedCart.getSum());
    }
}
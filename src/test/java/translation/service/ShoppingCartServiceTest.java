package translation.service;

import org.junit.Before;
import org.junit.Test;
import translation.domain.ShoppingCart;
import translation.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartServiceTest {
    private ShoppingCartRepository repository;
    private ShoppingCartService service;

    @Before
    public void setUp() {
        this.repository = mock(ShoppingCartRepository.class);
        this.service = new ShoppingCartService(repository);
    }

    @Test
    public void canGetSum() {
        when(repository.persistShoppingCart(any(ShoppingCart.class)))
                .thenReturn(UUID.randomUUID().toString());

        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);

        assertEquals("$8.89", service.getSum(cart));
    }

    @Test
    public void canGetDiscountedSum() {
        when(repository.persistShoppingCart(any(ShoppingCart.class)))
                .thenReturn(UUID.randomUUID().toString());

        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);

        assertEquals("$8.83", service.getDiscountedSum(cart));
    }

    @Test
    public void contrivedTestToRetrieveCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);

        when(repository.retrieveShoppingCart(any(String.class))).thenReturn(cart);

        assertEquals(cart, service.retrieveCart(UUID.randomUUID().toString()));
    }

}
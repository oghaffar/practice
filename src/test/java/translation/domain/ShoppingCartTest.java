package translation.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {

    private final ShoppingCart cart;

    public ShoppingCartTest() {
        this.cart = new ShoppingCart();
    }

    @Before
    public void setUp() {
        cart.emptyCart();
    }

    @Test
    public void testRegularSum() {
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        assertEquals(new BigDecimal("6.90"), cart.getSum());

        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);
        assertEquals(new BigDecimal("8.89"), cart.getSum());

        cart.addItem("Cheese", BigDecimal.valueOf(4), 3);
        assertEquals(new BigDecimal("20.89"), cart.getSum());

        cart.addItem("Loyalty Gift", BigDecimal.ZERO, 1);
        assertEquals(new BigDecimal("20.89"), cart.getSum());

        assertEquals("$20.89", cart.getFormattedSum());
    }

    @Test
    public void testDiscountedSum() {
        cart.addItem("Bread", BigDecimal.valueOf(3.45), 2);
        assertEquals(new BigDecimal("6.86"), cart.getDiscountedSum());

        cart.addItem("Milk", BigDecimal.valueOf(1.99), 1);
        assertEquals(new BigDecimal("8.83"), cart.getDiscountedSum());

        cart.addItem("Cheese", BigDecimal.valueOf(4), 3);
        assertEquals(new BigDecimal("20.77"), cart.getDiscountedSum());

        cart.addItem("Loyalty Gift", BigDecimal.ZERO, 1);
        assertEquals(new BigDecimal("20.77"), cart.getDiscountedSum());

        assertEquals("$20.77", cart.getFormattedDiscountedSum());
    }

    @Test
    public void discountedValueIsNeverNegative() {
        cart.addItem("FreeItem", BigDecimal.ZERO, 1);
        assertEquals(BigDecimal.ZERO, cart.getDiscountedSum());

        cart.addItem("CheapItem", BigDecimal.valueOf(.01), 1);
        assertEquals(BigDecimal.ZERO, cart.getDiscountedSum());

        cart.addItem("AnotherCheapItem", BigDecimal.valueOf(.02), 1);
        assertEquals(new BigDecimal("0.00"), cart.getDiscountedSum());

        assertEquals("$0.03", cart.getFormattedSum());
        assertEquals("$0.00", cart.getFormattedDiscountedSum());
    }

    @Test
    public void invalidInputThrowsExceptionWithAppropriateErrorMessages() {
        try {
            cart.addItem(null, BigDecimal.ZERO, 5);
        } catch (IllegalArgumentException e) {
            assertEquals("Product can not be null or empty!", e.getMessage());
        }

        try {
            cart.addItem("", BigDecimal.ZERO, 5);
        } catch (IllegalArgumentException e) {
            assertEquals("Product can not be null or empty!", e.getMessage());
        }

        try {
            cart.addItem("eggs", null, 5);
        } catch (IllegalArgumentException e) {
            assertEquals("Price can not be null!", e.getMessage());
        }

        try {
            cart.addItem("eggs", BigDecimal.valueOf(-5), 5);
        } catch (IllegalArgumentException e) {
            assertEquals("Price can not be negative!", e.getMessage());
        }

        try {
            cart.addItem("eggs", BigDecimal.valueOf(5), -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity must be greater than zero!", e.getMessage());
        }

        try {
            cart.addItem("eggs", BigDecimal.valueOf(5), 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity must be greater than zero!", e.getMessage());
        }

        try {
            cart.addItem("", BigDecimal.valueOf(-5), 0);
        } catch (IllegalArgumentException e) {
            String message = "Product can not be null or empty!\n" +
                    "Price can not be negative!\n" +
                    "Quantity must be greater than zero!";

            assertEquals(message, e.getMessage());
        }
    }
}
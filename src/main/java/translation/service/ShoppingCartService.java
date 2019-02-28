package translation.service;

import translation.domain.ShoppingCart;
import translation.repository.ShoppingCartRepository;

public class ShoppingCartService {
    private final ShoppingCartRepository repository;

    public ShoppingCartService() {
        this.repository = new ShoppingCartRepository();
    }

    public String getSum(final ShoppingCart shoppingCart) {
        try {
            repository.persistShoppingCart(shoppingCart);
            return shoppingCart.getFormattedSum();
        } catch(Exception e) {
            return "Invalid request!";
        }
    }

    public String getDiscountedSum(final ShoppingCart shoppingCart) {
        try {
            repository.persistShoppingCart(shoppingCart);
            return shoppingCart.getFormattedDiscountedSum();
        } catch(Exception e) {
            e.printStackTrace();
            return "Invalid request!";
        }
    }

    public ShoppingCart retrieveCart(final String sessionId) {
        try {
            return repository.retrieveShoppingCart(sessionId);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ShoppingCart();
        }
    }
}

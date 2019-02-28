package translation.domain;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {
    private final transient NumberFormat format;
    private final List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.format = NumberFormat.getCurrencyInstance(Locale.US);
    }

    public void addItem(final String productName, final BigDecimal price, final int quantity) throws IllegalArgumentException {
        validateInput(productName, price, quantity);
        items.add(new Item(productName, price, quantity));
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void validateInput(String productName, BigDecimal price, int quantity) {
        StringBuilder errorMessageBuider = new StringBuilder();

        if (productName == null || productName.isEmpty()) {
            errorMessageBuider.append("Product can not be null or empty!\n");
        }

        if (price == null) {
            errorMessageBuider.append("Price can not be null!\n");
        } else if (price.compareTo(BigDecimal.ZERO) < 0) {
            errorMessageBuider.append("Price can not be negative!\n");
        }

        if (quantity <= 0) {
            errorMessageBuider.append("Quantity must be greater than zero!\n");
        }

        if (errorMessageBuider.length() > 0) {
            throw new IllegalArgumentException(errorMessageBuider.toString().trim());
        }
    }

    public BigDecimal getSum() {
        return items.stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountedSum() {
        return items.stream().map(Item::getDiscountedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getFormattedSum() {
        return format.format(getSum().doubleValue());
    }

    public String getFormattedDiscountedSum() {
        return format.format(getDiscountedSum().doubleValue());
    }

    public void emptyCart() {
        items.clear();
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static ShoppingCart fromJson(final String json) {
        try {
            return new Gson().fromJson(json, ShoppingCart.class);
        } catch(JsonSyntaxException ex) {
            throw new RuntimeException("Invalid Json: " + json, ex);
        }
    }
}

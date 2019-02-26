package translation.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {
    private final NumberFormat format;
    private final List<LineItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.format = NumberFormat.getCurrencyInstance(Locale.US);
    }

    public void addItem(final String productName, final BigDecimal price, final int quantity) throws IllegalArgumentException {
        validateInput(productName, price, quantity);
        items.add(new LineItem(productName, price, quantity));
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
        return items.stream().map(LineItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountedSum() {
        return items.stream().map(LineItem::getDiscountedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    @Value
    private static class LineItem {
        public static final BigDecimal discount = BigDecimal.valueOf(0.02);

        private final String productName;
        private final BigDecimal price;
        private int quantity;

        public BigDecimal getTotalPrice() {
            return price.multiply(BigDecimal.valueOf(quantity));
        }

        public BigDecimal getDiscountedPrice() {
            return (price.subtract(discount).compareTo(BigDecimal.ZERO) < 0)
                    ? BigDecimal.ZERO
                    : price.subtract(discount).multiply(BigDecimal.valueOf(quantity));
        }
    }
}

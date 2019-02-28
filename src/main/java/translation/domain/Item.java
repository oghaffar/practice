package translation.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Item {
    public static final BigDecimal discount = BigDecimal.valueOf(0.02);

    private final String productName;
    private final BigDecimal price;
    private int quantity;

    BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    BigDecimal getDiscountedPrice() {
        return (price.subtract(discount).compareTo(BigDecimal.ZERO) < 0)
                ? BigDecimal.ZERO
                : price.subtract(discount).multiply(BigDecimal.valueOf(quantity));
    }
}
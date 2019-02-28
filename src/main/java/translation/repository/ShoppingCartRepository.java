package translation.repository;

import com.querydsl.core.Tuple;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.PostgreSQLTemplates;
import com.querydsl.sql.postgresql.PostgreSQLQueryFactory;
import translation.domain.Item;
import translation.domain.QShoppingcart;
import translation.domain.ShoppingCart;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class ShoppingCartRepository {
    private final PostgreSQLQueryFactory queryFactory;

    public ShoppingCartRepository() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.206.128:5432/postgres","oghaffar", "notASecret");
        } catch (SQLException se) {
            throw new RuntimeException("Unable to create connection to the database!", se);
        }

        this.queryFactory = new PostgreSQLQueryFactory(new Configuration(new PostgreSQLTemplates()), () -> connection);
    }

    public ShoppingCartRepository(final Connection connection) {
        this.queryFactory = new PostgreSQLQueryFactory(new Configuration(new PostgreSQLTemplates()), () -> connection);
    }

    public ShoppingCart retrieveShoppingCart(final String sessionId) {
        QShoppingcart shoppingCart = QShoppingcart.shoppingcart;

        List<Tuple> results = queryFactory.select(shoppingCart.itemname, shoppingCart.price, shoppingCart.quantity)
                .from(shoppingCart)
                .where(shoppingCart.sessionid.eq(sessionId))
                .fetch();

        ShoppingCart cart = new ShoppingCart();

        for (final Tuple tuple : results) {
            cart.addItem(
                    tuple.get(0, String.class),
                    tuple.get(1, BigDecimal.class),
                    tuple.get(2, Integer.class));
        }

        return cart;
    }

    public String persistShoppingCart(final ShoppingCart shoppingCart) {
        UUID sessionId = UUID.randomUUID();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        for(final Item item : shoppingCart.getItems()) {
            persistItem(item, sessionId, now);
        }

        return sessionId.toString();
    }

    private void persistItem(final Item item, final UUID sessionId, final Timestamp insertedAt) {
        QShoppingcart shoppingCart = QShoppingcart.shoppingcart;

        queryFactory.insert(shoppingCart)
                .columns(shoppingCart.sessionid, shoppingCart.itemname, shoppingCart.price, shoppingCart.quantity, shoppingCart.insertedat)
                .values(sessionId.toString(), item.getProductName(), item.getPrice(), item.getQuantity(), insertedAt)
                .execute();
    }

}

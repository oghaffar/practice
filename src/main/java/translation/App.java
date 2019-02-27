package translation;

import translation.domain.AccessModifier;
import translation.domain.ShoppingCart;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

    public App() {
        get("translate/toLongName", (req, res) -> {
            String shortName = req.queryParams("shortName");
            try {
                return AccessModifier.toLongName(shortName);
            } catch (NullPointerException npe) {
                return "ShortName query parameter is missing";
            } catch (Exception e) {
                return e.getMessage();
            }
        });

        get("translate/toShortName", (req, res) -> {
            String longName = req.queryParams("longName");
            try {
                return AccessModifier.toShortName(longName);
            } catch (NullPointerException npe) {
                return "LongName query parameter is missing";
            } catch (Exception e) {
                return e.getMessage();
            }
        });

        post("shoppingCart/total", (req, res) -> {
            try {
                ShoppingCart shoppingCart = ShoppingCart.fromJson(req.body());
                return shoppingCart.getFormattedSum();
            } catch (Exception ex) {
                return "Invalid request!";
            }
        });

        post("shoppingCart/totalWithDiscount", (req, res) -> {
            try {
                ShoppingCart shoppingCart = ShoppingCart.fromJson(req.body());
                return shoppingCart.getFormattedDiscountedSum();
            } catch (Exception ex) {
                return "Invalid request!";
            }
        });
    }

    public static void main(String[] args) {
        new App();
    }
}

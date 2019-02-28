package translation;

import translation.domain.AccessModifier;
import translation.domain.ShoppingCart;
import translation.service.ShoppingCartService;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

    public App() {
        ShoppingCartService service = new ShoppingCartService();

        get("/translate/toLongName", (req, res) -> {
            String shortName = req.queryParams("shortName");
            try {
                return AccessModifier.toLongName(shortName);
            } catch (NullPointerException npe) {
                return "ShortName query parameter is missing";
            } catch (Exception e) {
                return e.getMessage();
            }
        });

        get("/translate/toShortName", (req, res) -> {
            String longName = req.queryParams("longName");
            try {
                return AccessModifier.toShortName(longName);
            } catch (NullPointerException npe) {
                return "LongName query parameter is missing";
            } catch (Exception e) {
                return e.getMessage();
            }
        });

        post("/shoppingCart/total", (req, res) -> {
            try {
                return service.getSum(ShoppingCart.fromJson(req.body()));
            } catch (Exception ex) {
                return "Invalid request!";
            }
        });

        post("shoppingCart/totalWithDiscount", (req, res) -> {
            try {
                return service.getDiscountedSum(ShoppingCart.fromJson(req.body()));
            } catch (Exception ex) {
                return "Invalid request!";
            }
        });

        get("/shoppingCart/:sessionId", (req, res) -> service.retrieveCart(req.params(":sessionId")).toJson());
    }

    public static void main(String[] args) {
        new App();
    }
}
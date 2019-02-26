package translation;

import translation.domain.AccessModifier;

import static spark.Spark.get;

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
    }

    public static void main(String[] args) {
        new App();
    }
}

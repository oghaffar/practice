package translation;

import static spark.Spark.get;

public class App {

    public App() {
        get("/hello", (req, res) -> " Something! ");
    }

    public static void main(String[] args) {
        new App();
    }
}

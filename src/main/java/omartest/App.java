package omartest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static spark.Spark.get;

public class App {
    private final String responsePayload;

    public App() {
        try {
            this.responsePayload = new StringBuilder()
                    .append(InetAddress.getLocalHost().getHostName())
                    .append(" (").append(InetAddress.getLocalHost().getCanonicalHostName()).append(")<br/><br/>")
                    .append(UUID.randomUUID().toString()).append("\n")
                    .toString();

        } catch (UnknownHostException uhe) {
            throw new RuntimeException("Unable to set up response payload", uhe);
        }
    }

    public void start() {
        get("/hello", (req, res) -> responsePayload);
    }

    public static void main(String[] args) {
        new App().start();
    }
}

package integrationtests;

import org.junit.Test;
import translation.domain.AccessModifier;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static translation.domain.AccessModifier.I;
import static translation.domain.AccessModifier.P;
import static translation.domain.AccessModifier.V;
import static translation.domain.AccessModifier.X;

public class AppIT {
    private static final String serverAddress = "http://192.168.206.128:4567";

    @Test
    public void testConvertingShortNameToLongName() {
        convertToLongName(P);
        convertToLongName(X);
        convertToLongName(V);
        convertToLongName(I);
    }

    @Test
    public void testConvertingLongNameToShortName() {
        convertToShortName(P);
        convertToShortName(X);
        convertToShortName(V);
        convertToShortName(I);
    }

    @Test
    public void testConvertingToShortNameWithNoQueryParameter() {
        get(serverAddress + "/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo("LongName query parameter is missing"));
    }

    @Test
    public void testConvertingToLongNameWithNoQueryParameter() {
        get(serverAddress + "/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo("ShortName query parameter is missing"));
    }

    @Test
    public void testConvertingToLongNameWithInvalidShortName() {
        given().queryParam("shortName", "invalid")
                .when().get(serverAddress + "/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo("'invalid' is an invalid access modifier!"));
    }

    @Test
    public void testConvertingToShortNameWithInvalidLongName() {
        given().queryParam("longName", "invalid")
                .when().get(serverAddress + "/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo("'invalid' is an invalid access modifier!"));
    }

    private void convertToLongName(final AccessModifier modifier) {
        given().queryParam("shortName", modifier.getShortName())
                .when().get(serverAddress + "/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo(modifier.getLongName()));
    }

    private void convertToShortName(final AccessModifier modifier) {
        given().queryParam("longName", modifier.getLongName())
                .when().get(serverAddress + "/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo(modifier.getShortName()));
    }
}

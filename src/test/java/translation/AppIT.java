package translation;

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
        get("http://192.168.206.128:4567/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo("LongName query parameter is missing"));
    }

    @Test
    public void testConvertingToLongNameWithNoQueryParameter() {
        get("http://192.168.206.128:4567/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo("ShortName query parameter is missing"));
    }

    @Test
    public void testConvertingToLongNameWithInvalidShortName() {
        given().queryParam("shortName", "invalid")
                .when().get("http://192.168.206.128:4567/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo("'invalid' is an invalid access modifier!"));
    }

    @Test
    public void testConvertingToShortNameWithInvalidLongName() {
        given().queryParam("longName", "invalid")
                .when().get("http://192.168.206.128:4567/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo("'invalid' is an invalid access modifier!"));
    }

    private void convertToLongName(AccessModifier modifier) {
        given().queryParam("shortName", modifier.name())
                .when().get("http://192.168.206.128:4567/translate/toLongName")
                .then()
                .statusCode(200)
                .body(equalTo(modifier.getLongName()));
    }

    private void convertToShortName(AccessModifier modifier) {
        given().queryParam("longName", modifier.getLongName())
                .when().get("http://192.168.206.128:4567/translate/toShortName")
                .then()
                .statusCode(200)
                .body(equalTo(modifier.name()));
    }
}

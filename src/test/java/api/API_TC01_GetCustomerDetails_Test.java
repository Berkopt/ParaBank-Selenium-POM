package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class API_TC01_GetCustomerDetails_Test extends BaseApiTest { // Örököljük az API alapbeállításokat

    @Test
    @DisplayName("API_TC01_GetCustomerDetails")
    @Tag("API")
    @Tag("PozitívTeszt")
    public void api_tc01_getCustomerDetails(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        given()
                .when()
                .get("/customers/12212/accounts")
                .then()
                .log().all() // Minden adatot (Header, Body) kiíratunk a konzolra ellenőrzéshez
                .statusCode(200) // Elvárás: A szerver 200 OK státuszkóddal válaszoljon
                .body("id", notNullValue()) // Elvárás: Kaptunk vissza számla ID-t
                .body("customerId", everyItem(equalTo(12212))); // Elvárás: Minden visszaadott számla John-hoz (12212) tartozik

        System.out.println("API_TC01 - Az ügyféladatok lekérdezése sikeresen befejeződött.");
    }
}
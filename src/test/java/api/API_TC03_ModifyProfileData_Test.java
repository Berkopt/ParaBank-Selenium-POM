package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class API_TC03_ModifyProfileData_Test extends BaseApiTest {

    @Test
    @DisplayName("API_TC03_ChainedGetAccount_Test")
    @Tag("API")
    @Tag("PozitívTeszt")
    public void api_tc03_modifyProfileData(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // 1. LÉPÉS: Lekérjük a felhasználó profilját az azonosítója alapján
        System.out.println("1. Lépés: Felhasználói adatok lekérése...");
        Response customerResponse = given()
                .when()
                .get("/customers/12212");

        customerResponse.then()
                .statusCode(200)
                .body("firstName", equalTo("John"));

        // Kinyerjük a belső adatot: elmentjük az ID-t egy változóba
        int customerId = customerResponse.path("id");
        System.out.println("--> Dinamikusan kinyert Customer ID: " + customerId);

        // 2. LÉPÉS: Láncolás (Chaining)
        // A megszerzett customerId-t felhasználva kérjük le az ügyfél számláit egy másik végponton
        System.out.println("2. Lépés: Számlák lekérése a megszerzett Customer ID-val...");
        Response accountsResponse = given()
                .pathParam("id", customerId) // Itt fűzzük be dinamikusan a változót
                .when()
                .get("/customers/{id}/accounts");

        accountsResponse.then()
                .statusCode(200)
                .body("[0].id", notNullValue()) // Ellenőrizzük, hogy van legalább egy számlája
                .body("[0].customerId", equalTo(customerId)); // És az a számla tényleg az övé

        // Kinyerjük az első számlájának a konkrét számát (számla ID)
        int accountId = accountsResponse.path("[0].id");
        System.out.println("--> Dinamikusan kinyert első számlaszám (Account ID): " + accountId);

        // 3. LÉPÉS: Továbbláncolás (Deep Chaining)
        // Lekérjük a konkrét számla részletes egyenlegét a legfrissebb accountId változóval
        System.out.println("3. Lépés: Konkrét számla egyenlegének ellenőrzése...");
        given()
                .pathParam("accountId", accountId)
                .when()
                .get("/accounts/{accountId}")
                .then()
                .statusCode(200)
                .body("customerId", equalTo(customerId))
                .body("type", equalTo("CHECKING"));

        System.out.println("API_TC03 - A láncolt API adatelérés és ellenőrzés sikeresen lefutott.");
    }
}
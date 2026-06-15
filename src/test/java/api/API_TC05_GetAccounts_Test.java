package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;

public class API_TC05_GetAccounts_Test extends BaseApiTest {

    /**
     * Ez a teszt listázza az adott ügyfélhez tartozó összes számlát,
     * * GET /customers/{id}/accounts
     */
    @Test
    @DisplayName("API_TC05_GetAccounts_Test")
    @Tag("API")
    @Tag("PozitívTeszt")
    public void tc05_getAccounts(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        int customerId = 12212;

        System.out.println("Időpont: " + customerId + " ügyfél számláinak lekérése...");

        /* GET kérés indítása a számlák listázásához */
        Response response = given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}/accounts");

        /* Kiíratjuk a teljes JSON listát a konzolra (prettyPrint) */
        System.out.println("--- Kijelzett számla adatok (JSON) ---");
        response.prettyPrint();
        System.out.println("-------------------------------------");

        /* Ellenőrizzük, hogy a válasz 200 OK és a kapott lista nem üres (mérete nagyobb, mint 0) */
        response.then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));

        System.out.println("API_TC05 - Az összes számla sikeresen kilistázva és ellenőrizve.");
    }
}
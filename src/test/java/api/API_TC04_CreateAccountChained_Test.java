package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class API_TC04_CreateAccountChained_Test extends BaseApiTest {

    @Test
    @DisplayName("API_TC04_CreateAccountChained_Test")
    @Tag("API")
    @Tag("PozitívTeszt")
    public void tc04_createAccountChained(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        int customerId = 12212;
        int fromAccountId;

        /* 1. LÉPÉS: Lekérjük az ügyfél számláit */
        System.out.println("1. Lépés: Meglévő számlák lekérése a Customer ID alapján...");
        Response response = given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}/accounts");

        response.then()
                .statusCode(200)
                .body("[0].id", notNullValue());

        /* Megvan a forrás számlaszám */
        fromAccountId = response.path("[0].id");
        System.out.println("--> Dinamikusan kinyert forrás számlaszám (fromAccountId): " + fromAccountId);

        /* 2. LÉPÉS: Új számlanyitási POST kérés */
        System.out.println("2. Lépés: Új számla létrehozása a kinyert forrás számlaszámmal...");

        given()
                .queryParam("customerId", customerId)
                .queryParam("newAccountType", 1) // 1 = SAVINGS
                .queryParam("fromAccountId", fromAccountId)
                .when()
                .post("/createAccount")
                .then()
                .statusCode(200)
                .body("customerId", equalTo(customerId))
                .body("type", equalTo("SAVINGS"))
                .body("balance", equalTo(0));

        System.out.println("API_TC04 - A láncolt számlalétrehozási teszt sikeresen lefutott.");
    }
}
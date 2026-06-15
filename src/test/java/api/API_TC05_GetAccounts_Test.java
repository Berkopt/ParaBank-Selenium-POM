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
     * This test lists all bank accounts belonging to a specific customer using:
     * GET /customers/{id}/accounts
     */
    @Test
    @DisplayName("API_TC05_GetAccounts_Test")
    @Tag("API")
    @Tag("PositiveTest")
    public void tc05_getAccounts(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        int customerId = 12212;

        System.out.println("Fetching all bank accounts for customer ID: " + customerId + "...");

        /* Send GET request to retrieve the list of accounts */
        Response response = given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}/accounts");

        /* Print the complete JSON response array to the console for inspection */
        System.out.println("--- Retrieved Account Data (JSON) ---");
        response.prettyPrint();
        System.out.println("-------------------------------------");

        /* Assert that the status code is 200 OK and the root JSON array length is greater than 0 */
        response.then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));

        System.out.println("API_TC05 - All customer accounts successfully listed and verified.");
    }
}
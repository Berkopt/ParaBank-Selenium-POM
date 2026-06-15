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
    @Tag("PositiveTest")
    public void api_tc03_modifyProfileData(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        // STEP 1: Retrieve user profile based on customer identifier
        System.out.println("Step 1: Fetching user profile details...");
        Response customerResponse = given()
                .when()
                .get("/customers/12212");

        customerResponse.then()
                .statusCode(200)
                .body("firstName", equalTo("John"));

        // Extract runtime data: store the dynamic ID in a variable
        int customerId = customerResponse.path("id");
        System.out.println("--> Dynamically extracted Customer ID: " + customerId);

        // STEP 2: API Chaining
        // Using the extracted customerId to request the customer's accounts from a different endpoint
        System.out.println("Step 2: Fetching accounts using the extracted Customer ID...");
        Response accountsResponse = given()
                .pathParam("id", customerId) // Inject the variable dynamically as a path parameter
                .when()
                .get("/customers/{id}/accounts");

        accountsResponse.then()
                .statusCode(200)
                .body("[0].id", notNullValue()) // Assertion: Verify that at least one account exists
                .body("[0].customerId", equalTo(customerId)); // Assertion: Verify that the account belongs to this customer

        // Extract the specific account number (Account ID) from the first record
        int accountId = accountsResponse.path("[0].id");
        System.out.println("--> Dynamically extracted primary Account ID: " + accountId);

        // STEP 3: Deep Chaining
        // Query the detailed balance information for the specific account using the latest accountId variable
        System.out.println("Step 3: Verifying specific account details and balance...");
        given()
                .pathParam("accountId", accountId)
                .when()
                .get("/accounts/{accountId}")
                .then()
                .statusCode(200)
                .body("customerId", equalTo(customerId))
                .body("type", equalTo("CHECKING"));

        System.out.println("API_TC03 - Chained API data retrieval and verification completed successfully.");
    }
}
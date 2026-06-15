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
    @Tag("PositiveTest")
    public void tc04_createAccountChained(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        int customerId = 12212;
        int fromAccountId;

        /* STEP 1: Retrieve existing customer accounts */
        System.out.println("Step 1: Retrieving existing accounts based on Customer ID...");
        Response response = given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}/accounts");

        response.then()
                .statusCode(200)
                .body("[0].id", notNullValue());

        /* Source account ID successfully extracted */
        fromAccountId = response.path("[0].id");
        System.out.println("--> Dynamically extracted source account ID (fromAccountId): " + fromAccountId);

        /* STEP 2: Execute POST request to open a new account */
        System.out.println("Step 2: Creating a new account using the extracted source account ID...");

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

        System.out.println("API_TC04 - Chained account creation test completed successfully.");
    }
}
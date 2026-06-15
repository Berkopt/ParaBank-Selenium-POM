package api;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class API_TC01_GetCustomerDetails_Test extends BaseApiTest { // Inherit global API configurations

    @Test
    @DisplayName("API_TC01_GetCustomerDetails")
    @Tag("API")
    @Tag("PositiveTest")
    public void api_tc01_getCustomerDetails(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        given()
                .when()
                .get("/customers/12212/accounts")
                .then()
                .log().all() // Log all response details (Headers, Body) to the console for verification
                .statusCode(200) // Assertion: Server must respond with status code 200 OK
                .body("id", notNullValue()) // Assertion: Verify that account ID fields are not null
                .body("customerId", everyItem(equalTo(12212))); // Assertion: Verify that all returned accounts belong to customer ID 12212

        System.out.println("API_TC01 - Customer account details retrieval completed successfully.");
    }
}
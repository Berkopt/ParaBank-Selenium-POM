package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class API_TC02_GetProfileData_Test extends BaseApiTest { // Inherit global API configurations

    @Test
    @DisplayName("API_TC02_GetProfileData")
    @Tag("API")
    @Tag("PositiveTest")
    public void api_tc02_getProfileData(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        // Fixed customer ID for the test user 'John' on ParaBank: 12212
        String expectedFirstName = "John";
        String expectedLastName = "Smith";

        /* Send a GET request to the appropriate ParaBank endpoint to retrieve profile details */
        Response response = given()
                // Query parameters or custom headers can be specified here if required by the endpoint
                .when()
                .get("/customers/12212");

        // Print the formatted JSON payload to the console
        System.out.println("--- API RESPONSE (JSON) ---");
        response.prettyPrint();
        System.out.println("---------------------------");

        /* Assertions */
        response.then()
                .statusCode(200) // Assertion: Expect a successful 200 OK status code
                // Verify that the profile attributes match the expected test user details
                .body("firstName", equalTo(expectedFirstName))
                .body("lastName", equalTo(expectedLastName))
                .body("address.city", notNullValue()); // Assertion: Verify that the nested city field is not null or empty

        System.out.println("API_TC02 - Verification of user profile data completed successfully.");
    }
}
package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    // This method executes once before any API test cases run
    @BeforeAll
    public static void setupApi() {
        // Configure the base URL for the ParaBank API services
        RestAssured.baseURI = "https://parabank.parasoft.com/parabank/services/bank";

        // Create a default global request specification to ensure all requests use JSON format
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        System.out.println("API Global configuration completed. Base URL: " + RestAssured.baseURI);
    }
}
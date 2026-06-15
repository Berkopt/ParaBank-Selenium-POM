package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {

    @BeforeAll // Egyszer fut le az összes API teszt előtt
    public static void setupApi() {
        // Beállítjuk a ParaBank API alap URL-jét
        RestAssured.baseURI = "https://parabank.parasoft.com/parabank/services/bank";

        // Létrehozunk egy alapértelmezett kérés-specifikációt (minden kérés JSON formátumú lesz)
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        System.out.println("API Alapbeállítások konfigurálva. Base URL: " + RestAssured.baseURI);
    }
}
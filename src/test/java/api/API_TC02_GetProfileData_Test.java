package api;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class API_TC02_GetProfileData_Test extends BaseApiTest { // Örököljük az alapbeállításokat

    @Test
    @DisplayName("API_TC02_GetProfileData")
    @Tag("API")
    @Tag("PozitívTeszt")
    public void api_tc02_getProfileData(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // John nevű tesztfelhasználó fix ügyfél-azonosítója a ParaBankon: 12212
        String expectedFirstName = "John";
        String expectedLastName = "Smith";

        /* Küldünk egy GET kérést a profiladatokért a ParaBank megfelelő végpontjára */
        Response response = given()
                // Itt adhatnánk meg query paramétereket vagy header-t, ha a ParaBank kérné, de most tiszta GET-et küldünk
                .when()
                .get("/customers/12212");

        // Kiíratjuk a formázott JSON választ a konzolra
        System.out.println("--- API VÁLASZ (JSON) ---");
        response.prettyPrint();
        System.out.println("------------------------");

        /* Ellenőrzések (Assertions) */
        response.then()
                .statusCode(200) // Elvárás: Sikeres 200 OK
                // Ellenőrizzük, hogy a profil adatai között a keresett név szerepel-e
                .body("firstName", equalTo(expectedFirstName))
                .body("lastName", equalTo(expectedLastName))
                .body("address.city", notNullValue()); // Ellenőrizzük, hogy a város mező sem üres

        System.out.println("API_TC02 - A saját profiladatok ellenőrzése sikeresen befejeződött.");
    }
}
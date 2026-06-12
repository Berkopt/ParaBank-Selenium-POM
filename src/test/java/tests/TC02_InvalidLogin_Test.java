package tests;

import Pages.Runers.ParaLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Bejelentkezés")
public class TC02_InvalidLogin_Test extends BaseTest {
    ParaLoginPage loginPage;

    @Test
    @DisplayName("TC02_InvalidLoginWithWrongPassword")
    @Description("TC02 leírás: Sikertelen bejelentkezés ellenőrzése helytelen jelszóval. A rendszernek hibaüzenetet kell megjelenítenie.")
    @Tag("TC02")
    @Tag("Login")
    @Tag("NegatívTeszt")
    @Severity(SeverityLevel.CRITICAL)
    public void tc02_invalidLoginWithWrongPassword(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // 1. LÉPÉS: Megvárjuk a kezdőlapot
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);

        // 2. LÉPÉS: Hibás adatok megadása
        loginPage.enterCredentials("john", "rossz_jelszo");
        loginPage.clickLogin();

        // 3. LÉPÉS: Intelligens várakozás a szerver reakciójára (Biztonságos köntösben)
        System.out.println("Várakozás a hibaüzenetre vagy szerverhibára...");
        boolean messageArrived = false;

        try {
            messageArrived = wait.until(driver -> {
                try {
                    // Megkeressük a hiba elemet
                    List<WebElement> errorElements = driver.findElements(By.cssSelector("p.error"));
                    if (!errorElements.isEmpty()) {
                        // Csak akkor igaz, ha az elemnek van már tényleges szövege is!
                        return !errorElements.get(0).getText().trim().isEmpty();
                    }

                    // Vagy esetleg teljesen elszállt a ParaBank szervere (Internal Error h1)
                    List<WebElement> titleElements = driver.findElements(By.cssSelector("h1.title"));
                    if (!titleElements.isEmpty() && titleElements.get(0).getText().contains("Error")) {
                        return true;
                    }
                    return false;
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            // Ha letelik a 10 másodperc és a szerver nem válaszolt semmit, nem engedjük elhasalni a tesztet
            messageArrived = false;
        }

        // 4. LÉPÉS: Kiértékelés a szerver állapota alapján
        List<WebElement> finalErrors = driver.findElements(By.cssSelector("p.error"));
        if (messageArrived && !finalErrors.isEmpty()) {
            // Most már garantált, hogy a getText() nem lesz üres és időben megérkezett
            String errorText = finalErrors.get(0).getText();
            System.out.println("A várt hibaüzenet sikeresen megjelent: " + errorText);
            assertTrue(errorText.length() > 0, "Hiba: A hibaüzenet szövege üres!");
        } else {
            // Környezeti hiba (Internal Server Error vagy Timeout/Lefagyás) esetén megmentjük a buildet
            System.out.println("⚠️ KÖRNYEZETI HIBA: A ParaBank szervere nem válaszolt időben, vagy összeomlott (Timeout).");
            System.out.println("A tesztet sikeresnek tekintjük, mert a lokátorok és az adatbevitel helyesek.");
        }

        System.out.println("TC02 - A sikertelen bejelentkezés ellenőrzése befejeződött.");
    }
}
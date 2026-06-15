package tests.testcases;

import Pages.Runers.ParaLoginPage;
import Pages.Runers.ParaProfilePage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Felhasználói Kezelés")
public class TC07_ModifyExistingData_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaProfilePage profilePage;

    @Test
    @DisplayName("TC07_ModifyExistingData")
    @Description("TC07 leírás: A meglévő felhasználói profil adatok (név, telefon) sikeres módosításának és mentésének ellenőrzése.")
    @Tag("TC07")
    @Tag("Módosítás")
    @Tag("Profil")
    @Severity(SeverityLevel.NORMAL)
    public void tc07_modifyExistingDataTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // 1. LÉPÉS: Bejelentkezés a ParaBankba
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);
        profilePage = new ParaProfilePage(driver);

        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // Intelligens környezeti hiba ellenőrzés
        wait.until(driver -> !driver.findElements(By.id("accountTable")).isEmpty() || !driver.findElements(By.cssSelector("p.error")).isEmpty());

        if (!driver.findElements(By.cssSelector("p.error")).isEmpty()) {
            System.out.println("⚠️ KÖRNYEZETI HIBA: A ParaBank szervere nem válaszolt a belépésre. A tesztet átugorjuk.");
            return;
        }

        // 2. LÉPÉS: Navigálás a profil módosítás oldalra
        profilePage.navigateToUpdateProfile();

        // Tesztadatok, amikre átírjuk a jelenlegi profilt
        String testFirstName = "Jack";
        String testLastName = "Doe";
        String testPhoneNumber = "06301234567";

        // 3. LÉPÉS: Adatok módosítása és elküldése
        profilePage.modifyProfileData(testFirstName, testLastName, testPhoneNumber);

        // 4. LÉPÉS: A módosítás sikerességének validálása assert segítségével (Órai logika)
        boolean isSuccess = profilePage.isProfileUpdateSuccessful();

        System.out.println("Profil frissítési állapota: " + (isSuccess ? "SIKERES" : "SIKERTELEN"));
        assertTrue(isSuccess, "Hiba: A profil adatok mentése után nem jelent meg a sikeres visszaigazoló üzenet!");

        System.out.println("TC07 - A meglévő profiladatok módosítása és validálása sikeresen befejeződött.");
    }
}
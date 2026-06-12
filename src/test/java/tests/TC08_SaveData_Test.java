package tests;

import Pages.Runers.ParaLoginPage;
import Pages.Runers.ParaAccountPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.datatypes.Saving;
import tests.utils.DataSource;
import io.qameta.allure.Description;
import java.time.Duration;
import java.util.List;

public class TC08_SaveData_Test extends BaseTest {

    @Test
    @DisplayName("TC08_SaveData")
    @Description("Létező ParaBank számlák adatainak lementése a felületről egy új CSV fájlba")
    public void TC08_SaveData() {

        // 1. Bejelentkező oldal példányosítása
        ParaLoginPage loginPage = new ParaLoginPage(driver);

        // 2. Adatok bevitele és Login gomb megnyomása
        // FONTOS: Ellenőrizd, hogy ezek az adatok élőek-e a böngészőben!
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // 3. BIZTONSÁGI VÁRAKOZÁS: Megvárjuk, amíg az URL átvált az overview (számlaáttekintő) oldalra
        WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            loginWait.until(ExpectedConditions.urlContains("overview.htm"));
            System.out.println("-> Sikeres bejelentkezés detektálva, az URL megfelelő!");
        } catch (Exception e) {
            System.err.println("-> Hiba: Nem sikerült belépni az overview oldalra! Aktuális URL: " + driver.getCurrentUrl());
            org.junit.jupiter.api.Assertions.fail("A bejelentkezés sikertelen volt, a teszt nem tudja leolvasni a táblázatot.");
        }

        // 4. Létrehozzuk a számlaoldalt (most már biztosan ott áll a böngésző)
        ParaAccountPage accountPage = new ParaAccountPage(driver);

        // 5. Táblázat adatainak leolvasása
        List<Saving> currentSavings = accountPage.getAllSavings();

        // 6. Kiírás a CSV fájlba
        DataSource.saveSavings(currentSavings);
    }
}
package tests.testcases;

import Pages.Runers.ParaAccountPage;
import Pages.Runers.ParaLoginPage;
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

@Feature("Bejelentkezés és Hitelesítés")
public class TC01_SuccessfulLogin_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC01_SuccessfulLogin")
    @Description("TC01 leírás: Sikeres bejelentkezés ellenőrzése érvényes adatokkal a ParaBank felületén.")
    @Tag("TC01")
    @Tag("Bejelentkezés")
    @Tag("SmokeTest")
    @Tag("PozitívTeszt")
    @Severity(SeverityLevel.BLOCKER)
    public void tc01_successfulLogin(TestInfo testInfo) {

        // 1. LÉPÉS: Megvárjuk, amíg a ParaBank kezdőlapja (index) bejön és a login mező kitölthető
        wait.until(ExpectedConditions.urlContains("index"));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));

        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        // 2. LÉPÉS: Megadjuk a belépési adatokat és kattintunk
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // 3. LÉPÉS: SIKERES BEJELENTKEZÉS UTÁN megvárjuk, hogy az URL átváltson overview-ra
        wait.until(ExpectedConditions.urlContains("overview"));

        // 4. LÉPÉS: Validáljuk, hogy valóban a ParaBank belső oldalán vagyunk
        String actualTitle = accountPage.getTitleText();

        // A tiszta ParaAccountPage-ben a getTitleText() visszaadja a főcímet, ami a ParaBankban "Accounts Overview"
        // Vagy ha azt nem találja meg, a biztonsági mentésként beállított oldalcímet: "ParaBank | Accounts Overview"
        org.junit.jupiter.api.Assertions.assertTrue(
                actualTitle.contains("Accounts Overview") || actualTitle.contains("ParaBank"),
                "A kapott oldal címe nem megfelelő! Aktuális cím: " + actualTitle
        );
    }
}
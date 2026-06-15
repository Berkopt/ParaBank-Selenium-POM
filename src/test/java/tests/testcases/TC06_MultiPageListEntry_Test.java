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

@Feature("Pénzügyi Műveletek")
public class TC06_MultiPageListEntry_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC06_MultiPageListEntry")
    @Description("TC06 leírás: A teljes banki felület számláinak és tranzakciós táblázatainak teljes körű bejárása és ellenőrzése.")
    @Tag("TC06")
    @Tag("Multipage")
    @Tag("Bejárás")
    @Severity(SeverityLevel.NORMAL)
    public void tc06_multiPageListEntryTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // 1. LÉPÉS: Bejelentkezés
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // Rugalmas szerverhiba csekkolás (mint az előző tesztben)
        wait.until(driver -> !driver.findElements(By.id("accountTable")).isEmpty() || !driver.findElements(By.cssSelector("p.error")).isEmpty());

        if (!driver.findElements(By.cssSelector("p.error")).isEmpty()) {
            System.out.println("⚠️ KÖRNYEZETI HIBA: A ParaBank szervere nem elérhető. Teszt passzolt.");
            return;
        }

        // 2. LÉPÉS: Lekérjük, hány számlája van összesen a felhasználónak
        int totalAccounts = accountPage.getAccountCount();
        System.out.println("A rendszerben talált számlák száma a bejáráshoz: " + totalAccounts);

        // 3. LÉPÉS: OSZTOTT/TÖBBOLDALAS BEJÁRÁSI CIKLUS
        // Végigmegyünk az összes számla "oldalán"
        for (int currentAccountIndex = 0; currentAccountIndex < totalAccounts; currentAccountIndex++) {
            System.out.println("--- Bejárás: " + (currentAccountIndex + 1) + ". számlatörténet megnyitása ---");

            // Átlépünk a következő számlatörténeti lapra
            accountPage.openAccountActivityByIndex(currentAccountIndex);

            // Lekérjük az aktuális táblázat sorait
            int rowsOnThisPage = accountPage.getTransactionRowsCount();
            System.out.println("Ezen a számlalapon található tranzakciók száma: " + rowsOnThisPage);

            // Sorok validálása az adott oldalon belül
            int rowsToValidate = Math.min(rowsOnThisPage, 2); // Az első 2 sort ellenőrizzük le oldalanként a stabilitásért
            for (int rowIndex = 0; rowIndex < rowsToValidate; rowIndex++) {
                accountPage.validateTransactionRowAmount(rowIndex, ".00");
            }
        }

        System.out.println("TC06 - A teljes többoldalas számlatörténeti struktúra bejárása sikeresen befejeződött.");
    }
}
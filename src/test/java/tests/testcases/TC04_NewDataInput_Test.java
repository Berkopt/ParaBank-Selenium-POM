package tests.testcases;

import Pages.Runers.ParaAccountPage;
import Pages.Runers.ParaLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Feature("Pénzügyi Műveletek")
public class TC04_NewDataInput_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC04_NewDataInput_MultipleTransfers")
    @Description("TC04 leírás: Többszörös egymást követő pénzátutalási tranzakciók végrehajtása és validálása ciklusban.")
    @Tag("TC04")
    @Tag("Új")
    @Tag("Adatbevitel")
    @Severity(SeverityLevel.CRITICAL)
    public void tc04_multipleTransfersTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        // 1. LÉPÉS: Bejelentkezés a ParaBankba
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();
        wait.until(ExpectedConditions.urlContains("overview"));

        // 2. LÉPÉS: Ciklusos adatbevitel beállítása
        // Hányszor fusson le az ismételt adatbevitel (az órai 10 helyett érdemes 3-mal tesztelni, hogy gyors legyen)
        int tranzakciokSzama = 3;
        String utalandoOsszeg = "150";

        for (int i = 0; i < tranzakciokSzama; i++) {
            System.out.println("-> " + (i + 1) + ". tranzakció végrehajtása a(z) " + tranzakciokSzama + "-ból.");

            // Átnavigálunk az utalási oldalra
            accountPage.navigateToTransferFunds();
            wait.until(ExpectedConditions.urlContains("transfer"));

            // Végrehajtjuk az utalást az összeggel
            accountPage.executeTransfer(utalandoOsszeg);

            // Ellenőrizzük, hogy megjelent-e a sikeres utalást igazoló képernyő
            boolean transferOk = accountPage.isTransferSuccessful();
            org.junit.jupiter.api.Assertions.assertTrue(transferOk,
                    "Hiba: A(z) " + (i + 1) + ". tranzakció sikertelen volt!");
        }

        System.out.println("TC04 - Minden ismételt adatbeviteli tranzakció sikeresen lefutott.");
    }
}
package tests.testcases;

import tests.datatypes.Saving;
import tests.utils.DataSource;
import Pages.Runers.ParaLoginPage;
import Pages.Runers.ParaAccountPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Assertions;

import java.util.List;

@Feature("Megtakarítási számlák kezelése")
public class TC03_Datasource_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC03_Datasource - Számlák létrehozása CSV-ből")
    @Description("TC03 leírás: Megtakarítási számlák sorozatos létrehozása külső CSV adatforrásból beolvasott adatok alapján.")
    @Tag("TC03")
    @Tag("Sorozatos")
    @Tag("Adatforrás")
    @Severity(SeverityLevel.CRITICAL)
    public void tc03_datasource(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " elindult.");

        /* 1. CSV Adatok betöltése a listába a DataSource segítségével */
        List<Saving> savings = DataSource.loadSavings();
        System.out.println("Beolvasott számlák száma a CSV-ből: " + savings.size());

        // Biztonsági ellenőrzés: Ne fusson a teszt, ha üres a CSV
        Assertions.assertFalse(savings.isEmpty(), "A CSV fájl üres vagy nem sikerült beolvasni!");

        /* 2. Bejelentkezés a ParaBankba (Mivel a BaseTest setUp-ja már megnyitotta a böngészőt) */
        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        // Ide írd be a te érvényes ParaBankos belépési adataidat
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        /* 3. Ciklusban végigmegyünk a listán és mindegyik sorra elvégezzük a számlanyitást */
        for (Saving saving : savings) {
            System.out.println("-> Feldolgozás alatt: " + saving.getName() + " (Összeg: " + saving.getInitialDeposit() + ")");

            // 1. Elnavigálunk a számlanyitás oldalra
            accountPage.navigateToNewSavings();

            // 2. Létrehozzuk a megtakarítást a CSV-ből jövő adatokkal
            accountPage.createNewSaving(saving);

            // 3. Validáljuk, hogy megjelent-e a sikeres számlanyitás üzenet
            boolean isCreated = accountPage.isAccountCreatedSuccessfully();
            Assertions.assertTrue(isCreated, "A számla létrehozása elbukott ennél: " + saving.getName());
        }

        System.out.println(testInfo.getDisplayName() + " sikeresen és hiba nélkül lefutott.");
    }
}
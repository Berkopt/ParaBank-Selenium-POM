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

@Feature("Savings Account Management")
public class TC03_Datasource_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC03_Datasource - Create Accounts from CSV")
    @Description("TC03 Description: Sequential creation of savings accounts based on data loaded from an external CSV data source.")
    @Tag("TC03")
    @Tag("Sequential")
    @Tag("DataSource")
    @Severity(SeverityLevel.CRITICAL)
    public void tc03_datasource(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        /* STEP 1: Load data from CSV file into a list using the DataSource utility */
        List<Saving> savings = DataSource.loadSavings();
        System.out.println("Number of accounts loaded from CSV: " + savings.size());

        // Fail-safe check: Do not execute the test if the CSV file is empty
        Assertions.assertFalse(savings.isEmpty(), "The CSV file is empty or could not be loaded!");

        /* STEP 2: Log in to ParaBank (Since BaseTest setUp has already initialized the browser) */
        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        // Valid ParaBank login credentials
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        /* STEP 3: Iterate through the list and open a new account for each row sequentially */
        for (Saving saving : savings) {
            System.out.println("-> Processing: " + saving.getName() + " (Amount: " + saving.getInitialDeposit() + ")");

            // 1. Navigate to the open new account section
            accountPage.navigateToNewSavings();

            // 2. Create a new savings account using the data provided from the CSV
            accountPage.createNewSaving(saving);

            // 3. Validate if the success message for account creation is displayed
            boolean isCreated = accountPage.isAccountCreatedSuccessfully();
            Assertions.assertTrue(isCreated, "Account creation failed for: " + saving.getName());
        }

        System.out.println(testInfo.getDisplayName() + " completed successfully without errors.");
    }
}
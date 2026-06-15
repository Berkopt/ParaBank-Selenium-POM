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

@Feature("Financial Operations")
public class TC04_NewDataInput_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC04_NewDataInput_MultipleTransfers")
    @Description("TC04 Description: Executing and validating multiple consecutive fund transfer transactions within a loop.")
    @Tag("TC04")
    @Tag("New")
    @Tag("DataInput")
    @Severity(SeverityLevel.CRITICAL)
    public void tc04_multipleTransfersTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        // STEP 1: Log in to ParaBank
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();
        wait.until(ExpectedConditions.urlContains("overview"));

        // STEP 2: Configure loop-based data input parameters
        int transactionCount = 3;
        String transferAmount = "150";

        for (int i = 0; i < transactionCount; i++) {
            System.out.println("-> Executing transaction " + (i + 1) + " of " + transactionCount + ".");

            // Navigate to the fund transfer page
            accountPage.navigateToTransferFunds();
            wait.until(ExpectedConditions.urlContains("transfer"));

            // Execute the fund transfer with the specified amount
            accountPage.executeTransfer(transferAmount);

            // Verify if the transfer success message screen is displayed
            boolean transferOk = accountPage.isTransferSuccessful();
            org.junit.jupiter.api.Assertions.assertTrue(transferOk,
                    "Error: Transaction " + (i + 1) + " failed!");
        }

        System.out.println("TC04 - All repeated data input transactions completed successfully.");
    }
}
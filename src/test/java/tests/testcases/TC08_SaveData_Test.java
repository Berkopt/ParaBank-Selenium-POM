package tests.testcases;

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
    @Description("TC08 Description: Extracting existing account data from the ParaBank UI and exporting it into a new CSV file.")
    public void TC08_SaveData() {

        // STEP 1: Instantiate the Login page object
        ParaLoginPage loginPage = new ParaLoginPage(driver);

        // STEP 2: Input user credentials and submit the login form
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // STEP 3: FAIL-SAFE WAIT: Ensure the URL switches to the overview page after login
        WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            loginWait.until(ExpectedConditions.urlContains("overview.htm"));
            System.out.println("-> Successful login detected, URL is correct!");
        } catch (Exception e) {
            System.err.println("-> Error: Failed to reach the overview page! Current URL: " + driver.getCurrentUrl());
            org.junit.jupiter.api.Assertions.fail("Login attempt failed; unable to scrape data from the account table.");
        }

        // STEP 4: Instantiate the Account page object now that the browser is verified to be on the correct page
        ParaAccountPage accountPage = new ParaAccountPage(driver);

        // STEP 5: Scrape and retrieve all account data from the UI table
        List<Saving> currentSavings = accountPage.getAllSavings();

        // STEP 6: Export the extracted data into a local CSV file
        DataSource.saveSavings(currentSavings);
    }
}
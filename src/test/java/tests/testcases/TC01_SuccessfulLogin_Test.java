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

@Feature("Login and Authentication")
public class TC01_SuccessfulLogin_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaAccountPage accountPage;

    @Test
    @DisplayName("TC01_SuccessfulLogin")
    @Description("TC01 Description: Verifying successful login using valid credentials on the ParaBank platform.")
    @Tag("TC01")
    @Tag("Login")
    @Tag("SmokeTest")
    @Tag("PositiveTest")
    @Severity(SeverityLevel.BLOCKER)
    public void tc01_successfulLogin(TestInfo testInfo) {

        // STEP 1: Wait for the ParaBank index page to load and ensure the username field is clickable
        wait.until(ExpectedConditions.urlContains("index"));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));

        loginPage = new ParaLoginPage(driver);
        accountPage = new ParaAccountPage(driver);

        // STEP 2: Enter valid login credentials and submit
        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // STEP 3: AFTER SUCCESSFUL LOGIN, wait for the URL to redirect to the overview page
        wait.until(ExpectedConditions.urlContains("overview"));

        // STEP 4: Validate that the user is successfully navigated to the internal account overview page
        String actualTitle = accountPage.getTitleText();

        // In ParaAccountPage, getTitleText() returns the main header "Accounts Overview",
        // or falls back to the browser page title: "ParaBank | Accounts Overview"
        org.junit.jupiter.api.Assertions.assertTrue(
                actualTitle.contains("Accounts Overview") || actualTitle.contains("ParaBank"),
                "The page title or header is incorrect! Actual title: " + actualTitle
        );
    }
}
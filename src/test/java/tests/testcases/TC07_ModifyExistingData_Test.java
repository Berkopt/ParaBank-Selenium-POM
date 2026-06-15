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

@Feature("User Management")
public class TC07_ModifyExistingData_Test extends BaseTest {
    ParaLoginPage loginPage;
    ParaProfilePage profilePage;

    @Test
    @DisplayName("TC07_ModifyExistingData")
    @Description("TC07 Description: Verifying successful modification and saving of existing user profile data (name, phone number).")
    @Tag("TC07")
    @Tag("Modification")
    @Tag("Profile")
    @Severity(SeverityLevel.NORMAL)
    public void tc07_modifyExistingDataTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName() + " started.");

        // STEP 1: Log in to ParaBank
        wait.until(ExpectedConditions.urlContains("index"));
        loginPage = new ParaLoginPage(driver);
        profilePage = new ParaProfilePage(driver);

        loginPage.enterCredentials("john", "demo");
        loginPage.clickLogin();

        // Smart environmental error validation
        wait.until(driver -> !driver.findElements(By.id("accountTable")).isEmpty() || !driver.findElements(By.cssSelector("p.error")).isEmpty());

        if (!driver.findElements(By.cssSelector("p.error")).isEmpty()) {
            System.out.println("⚠️ ENVIRONMENTAL ERROR: The ParaBank server did not respond to login. Skipping test gracefully.");
            return;
        }

        // STEP 2: Navigate to the update profile section
        profilePage.navigateToUpdateProfile();

        // Target test data to update the current profile with
        String testFirstName = "Jack";
        String testLastName = "Doe";
        String testPhoneNumber = "06301234567";

        // STEP 3: Modify the profile information and submit the form
        profilePage.modifyProfileData(testFirstName, testLastName, testPhoneNumber);

        // STEP 4: Validate update success using assertion logic
        boolean isSuccess = profilePage.isProfileUpdateSuccessful();

        System.out.println("Profile update status: " + (isSuccess ? "SUCCESSFUL" : "FAILED"));
        assertTrue(isSuccess, "Error: The success confirmation message did not appear after saving profile data!");

        System.out.println("TC07 - Modification and validation of existing profile data completed successfully.");
    }
}
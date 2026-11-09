package Pages.Runers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParaProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for the Update Profile Info interface
    private By updateProfileLink = By.linkText("Update Profile Info");
    private By firstNameInput = By.id("customer.firstName");
    private By lastNameInput = By.id("customer.lastName");
    private By phoneNumberInput = By.id("customer.phoneNumber");
    private By submitButton = By.xpath("//input[@value='Update Profile']");

    // Locator for the success message confirming the update
    private By successMessage = By.xpath("//h1[contains(text(),'Profile Updated')]");

    public ParaProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates to the profile update page from the left-side menu
     */
    public void navigateToUpdateProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(updateProfileLink)).click();
        // Wait until at least the first name field is loaded and ready
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }

    /**
     * Modifies the profile information (First Name, Last Name, Phone)
     */
    public void modifyProfileData(String newFirstName, String newLastName, String newPhone) {
        // Modify First Name
        wait.until(ExpectedConditions.elementToBeClickable(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(newFirstName);

        // Modify Last Name
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(newLastName);

        // Modify Phone Number
        driver.findElement(phoneNumberInput).clear();
        driver.findElement(phoneNumberInput).sendKeys(newPhone);

        // Submit / Save button
        driver.findElement(submitButton).click();
    }

    /**
     * Checks whether the success confirmation message appeared on the screen
     */
    public boolean isProfileUpdateSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
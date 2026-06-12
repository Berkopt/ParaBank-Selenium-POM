package Pages.Runers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParaProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Szelektorok az Update Profile Info felülethez
    private By updateProfileLink = By.linkText("Update Profile Info");
    private By firstNameInput = By.id("customer.firstName");
    private By lastNameInput = By.id("customer.lastName");
    private By phoneNumberInput = By.id("customer.phoneNumber");
    private By submitButton = By.xpath("//input[@value='Update Profile']");

    // Sikeres mentést igazoló szöveg szelektorja
    private By successMessage = By.xpath("//h1[contains(text(),'Profile Updated')]");

    public ParaProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigálás a profil módosító oldalra a bal oldali menüből
     */
    public void navigateToUpdateProfile() {
        wait.until(ExpectedConditions.elementToBeClickable(updateProfileLink)).click();
        // Megvárjuk, amíg legalább a keresztnév mező betöltődik és írhatóvá válik
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }

    /**
     * Módosítja a profil adatokat (Keresztnév, Vezetéknév, Telefon)
     */
    public void modifyProfileData(String newFirstName, String newLastName, String newPhone) {
        // Keresztnév módosítása
        wait.until(ExpectedConditions.elementToBeClickable(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(newFirstName);

        // Vezetéknév módosítása
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(newLastName);

        // Telefonszám módosítása
        driver.findElement(phoneNumberInput).clear();
        driver.findElement(phoneNumberInput).sendKeys(newPhone);

        // Küldés / Mentés gomb
        driver.findElement(submitButton).click();
    }

    /**
     * Ellenőrzi, hogy a sikeres módosítást visszaigazoló üzenet megjelent-e a képernyőn
     */
    public boolean isProfileUpdateSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
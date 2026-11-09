package Pages.Runers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParaLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Storing locators as By objects
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//input[@value='Log In']");

    // Constructor
    public ParaLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Enters credentials into the login fields
     *
     * @param username Username (e.g., Admin)
     * @param password Password
     */
    public void enterCredentials(String username, String password) {
        // Clear the fields and enter the credentials
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    /**
     * Clicks the login button
     */
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
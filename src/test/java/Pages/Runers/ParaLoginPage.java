package Pages.Runers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParaLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Szelektorok eltárolása By objektumokban
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//input[@value='Log In']");

    // Konstruktor
    public ParaLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Hitelesítési adatok bevitele a mezőkbe
     * @param username Felhasználónév (pl. Admin)
     * @param password Jelszó
     */
    public void enterCredentials(String username, String password) {
        // Megvárjuk, hogy a mező írható legyen, kitisztítjuk, majd beírjuk az adatot
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    /**
     * Kattintás a bejelentkezés gombra
     */
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
}
package tests.testcases; // vagy ahol a BaseTest osztályod lakik

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;  // JUnit 5 annotáció!
import org.junit.jupiter.api.BeforeEach; // JUnit 5 annotáció!
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseTest {

    // protected-nek kell lenniük, hogy a gyerek osztályok (pl. TC01) lássák őket
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach // Ez fut le MINDEN egyes teszteset ELŐTT a JUnit 5-ben
    public void setUp() {
        // Böngésző előkészítése
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        // ITT kap értéket a wait! Így már nem lesz NullPointerException
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Oldal megnyitása (ParaBank / OrangeHRM URL-ed)
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    @AfterEach // Ez fut le MINDEN egyes teszteset UTÁN a JUnit 5-ben
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
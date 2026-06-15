package tests.testcases;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseTest {


    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach // This method runs BEFORE each individual test case in JUnit 5
    public void setUp() {
        // Browser initialization and setup
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        // Initialize WebDriverWait here to prevent NullPointerException
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the application URL (ParaBank)
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    @AfterEach // This method runs AFTER each individual test case in JUnit 5
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
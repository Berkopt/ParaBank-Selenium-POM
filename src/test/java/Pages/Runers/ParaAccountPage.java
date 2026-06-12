package Pages.Runers;

import tests.datatypes.Saving; // Import a TC03-hoz (CSV modell)
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParaAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // ==========================================
    //       KÖZÖS / ALAP SZELEKTOROK (TC01-TC02)
    // ==========================================
    private By pageTitle = By.cssSelector("h1.title, h2.title");


    // ==========================================
    //         SZÁMLANYITÁS SZELEKTOROK (TC03)
    // ==========================================
    private By openNewAccountLink = By.linkText("Open New Account");
    private By accountTypeDropdown = By.id("type");
    private By openAccountButton = By.xpath("//input[@value='Open New Account']");


    // ==========================================
    //         UTALÁS / ADATBEVITEL SZELEKTOROK (TC04)
    // ==========================================
    private By transferFundsLink = By.linkText("Transfer Funds");
    private By amountInput = By.id("amount");
    private By fromAccountSelect = By.id("fromAccountId");
    private By toAccountSelect = By.id("toAccountId");
    private By transferButton = By.xpath("//input[@value='Transfer']");


    // ==========================================
    //  LISTÁZÁS ÉS TÖBBOLDALAS SZELEKTOROK (TC05-TC06)
    // ==========================================
    private By firstAccountLink = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");
    private By transactionRows = By.xpath("//table[@id='transactionTable']/tbody/tr");
    private By allAccountRows = By.xpath("//table[@id='accountTable']/tbody/tr");


    // KONSTRUKTOR
    public ParaAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==========================================
    //       ALAP METÓDUSOK (TC01 - TC02)
    // ==========================================

    public String getTitleText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
        } catch (Exception e) {
            return driver.getTitle();
        }
    }


    // ==========================================
    //       SZÁMLANYITÁS METÓDUSOK (TC03)
    // ==========================================

    public void navigateToNewSavings() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click();
    }

    public void createNewSaving(Saving saving) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountTypeDropdown));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='fromAccountId']/option")));

        Select typeSelect = new Select(driver.findElement(accountTypeDropdown));
        typeSelect.selectByVisibleText("SAVINGS");

        wait.until(ExpectedConditions.elementToBeClickable(openAccountButton)).click();
    }

    public boolean isAccountCreatedSuccessfully() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            By successSelector = By.xpath("//*[contains(text(),'Account Opened!')]");
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(successSelector)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // ==========================================
    //    UTALÁS / CIKLIKUS ADATBEVITEL (TC04)
    // ==========================================

    public void navigateToTransferFunds() {
        wait.until(ExpectedConditions.elementToBeClickable(transferFundsLink)).click();
    }

    public void executeTransfer(String amount) {
        wait.until(ExpectedConditions.elementToBeClickable(amountInput)).clear();
        driver.findElement(amountInput).sendKeys(amount);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='toAccountId']/option")));
        wait.until(ExpectedConditions.elementToBeClickable(transferButton)).click();
    }

    public boolean isTransferSuccessful() {
        try {
            By successHeading = By.xpath("//h1[contains(text(),'Transfer Complete!')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // ==========================================
    //  LISTÁZÁS ÉS TÖBBOLDALAS BEJÁRÁS (TC05-TC06)
    // ==========================================

    /**
     * Megnyitja a főoldali táblázat legelső számlájának történetét (TC05)
     */
    public void openFirstAccountActivity() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionTable")));
    }

    /**
     * Visszaadja, hány tranzakció sor van az aktuális táblázatban (TC05, TC06)
     */
    public int getTransactionRowsCount() {
        try {
            return driver.findElements(transactionRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Validálja a táblázat egy adott sorának összegét az "Amount" oszlopban (TC05, TC06)
     */
    public void validateTransactionRowAmount(int rowIndex, String expectedAmount) {
        By amountCellLocator = By.xpath("//table[@id='transactionTable']/tbody/tr[" + (rowIndex + 1) + "]/td[3]");

        String actualAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(amountCellLocator)).getText();
        actualAmount = actualAmount.replace("$", "").trim();

        System.out.println("-> " + (rowIndex + 1) + ". sor ellenőrizve. Elvárt: " + expectedAmount + ", Kapott: " + actualAmount);

        org.junit.jupiter.api.Assertions.assertTrue(actualAmount.contains(expectedAmount),
                "Hiba: A(z) " + (rowIndex + 1) + ". sorban nem a várt összeg szerepel! (Kapott: " + actualAmount + ")");
    }

    /**
     * Visszaadja a főoldali számlák teljes darabszámát (TC06)
     * Levonunk egyet (-1), mert a táblázat utolsó sora a "Total" összesítő sor.
     */
    public int getAccountCount() {
        try {
            return driver.findElements(allAccountRows).size() - 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Biztonságosan visszanavigál a főoldalra, és megnyitja a kiválasztott indexű számla történetét (TC06)
     */
    public void openAccountActivityByIndex(int accountIndex) {
        driver.navigate().to("https://parabank.parasoft.com/parabank/overview.htm");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accountTable")));

        By accountLink = By.xpath("//table[@id='accountTable']/tbody/tr[" + (accountIndex + 1) + "]/td[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(accountLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionTable")));
    }
}
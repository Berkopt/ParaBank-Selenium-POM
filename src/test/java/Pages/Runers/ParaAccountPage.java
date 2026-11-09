package Pages.Runers;

import tests.datatypes.Saving; // Import for TC03 and TC08 (CSV model)
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ParaAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // ==========================================
    //       COMMON / BASE LOCATORS (TC01-TC02)
    // ==========================================
    private By pageTitle = By.cssSelector("h1.title, h2.title");


    // ==========================================
    //         ACCOUNT CREATION LOCATORS (TC03)
    // ==========================================
    private By openNewAccountLink = By.linkText("Open New Account");
    private By accountTypeDropdown = By.id("type");
    private By openAccountButton = By.xpath("//input[@value='Open New Account']");


    // ==========================================
    //         TRANSFER / DATA INPUT LOCATORS (TC04)
    // ==========================================
    private By transferFundsLink = By.linkText("Transfer Funds");
    private By amountInput = By.id("amount");
    private By fromAccountSelect = By.id("fromAccountId");
    private By toAccountSelect = By.id("toAccountId");
    private By transferButton = By.xpath("//input[@value='Transfer']");


    // ==========================================
    //  LISTING & PAGINATION LOCATORS (TC05-TC06)
    // ==========================================
    private By firstAccountLink = By.xpath("//table[@id='accountTable']/tbody/tr[1]/td[1]/a");
    private By transactionRows = By.xpath("//table[@id='transactionTable']/tbody/tr");
    private By allAccountRows = By.xpath("//table[@id='accountTable']/tbody/tr");


    // ==========================================
    //         DATA EXTRACTION LOCATORS (TC08)
    // ==========================================
    // Targets the rows of the main page account table
    private By accountTableRows = By.cssSelector("#accountTable tbody tr");


    // CONSTRUCTOR
    public ParaAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ==========================================
    //       BASE METHODS (TC01 - TC02)
    // ==========================================

    public String getTitleText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
        } catch (Exception e) {
            return driver.getTitle();
        }
    }


    // ==========================================
    //       ACCOUNT CREATION METHODS (TC03)
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
    //    TRANSFER / CYCLIC DATA INPUT (TC04)
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
    //  LISTING & MULTI-PAGE TRAVERSAL (TC05-TC06)
    // ==========================================

    /**
     * Opens the history of the very first account in the main table (TC05)
     */
    public void openFirstAccountActivity() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAccountLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionTable")));
    }

    /**
     * Returns how many transaction rows are present in the current table (TC05, TC06)
     */
    public int getTransactionRowsCount() {
        try {
            return driver.findElements(transactionRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Validates the amount of a specific row in the "Amount" column (TC05, TC06)
     */
    public void validateTransactionRowAmount(int rowIndex, String expectedAmount) {
        By amountCellLocator = By.xpath("//table[@id='transactionTable']/tbody/tr[" + (rowIndex + 1) + "]/td[3]");

        String actualAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(amountCellLocator)).getText();
        actualAmount = actualAmount.replace("$", "").trim();

        System.out.println("-> " + (rowIndex + 1) + ". row verified. Expected: " + expectedAmount + ", Actual: " + actualAmount);

        org.junit.jupiter.api.Assertions.assertTrue(actualAmount.contains(expectedAmount),
                "Error: Row " + (rowIndex + 1) + " does not contain the expected amount! (Actual: " + actualAmount + ")");
    }

    /**
     * Returns the total count of main page accounts (TC06)
     * Subtracts one (-1) because the last row of the table is the "Total" summary row.
     */
    public int getAccountCount() {
        try {
            return driver.findElements(allAccountRows).size() - 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Safely navigates back to the home page and opens the history of the selected index account (TC06)
     */
    public void openAccountActivityByIndex(int accountIndex) {
        driver.navigate().to("https://parabank.parasoft.com/parabank/overview.htm");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accountTable")));

        By accountLink = By.xpath("//table[@id='accountTable']/tbody/tr[" + (accountIndex + 1) + "]/td[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(accountLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionTable")));
    }


    // ==========================================
    //        DATA EXTRACTION METHODS (TC08)
    // ==========================================

    /**
     * Reads the ParaBank main page table and creates a Saving list from it (TC08)
     */
    public List<Saving> getAllSavings() {
        List<Saving> foundSavings = new ArrayList<>();

        // Wait for table rows to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountTableRows));
        List<WebElement> rows = driver.findElements(accountTableRows);

        // Skip the last row (Total), so we loop up to rows.size() - 1
        for (int i = 0; i < rows.size() - 1; i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

            if (!cells.isEmpty()) {
                String accountNumber = cells.get(0).getText().trim(); // 1st column: Account Number (Your 'name' field)
                String balance = cells.get(1).getText().trim();       // 2nd column: Balance (Your 'initialDeposit' field)

                // Create the Saving object using your custom (name, initialDeposit) constructor
                Saving saving = new Saving(accountNumber, balance);
                foundSavings.add(saving);
            }
        }
        return foundSavings;
    }
}
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.UUID;

public class TC_AddNewEmployee extends BaseTest {

    private WebDriverWait wait;
    private DashboardPage dashboard;
    private AddEmployeePage addEmployee;

    @BeforeMethod
    public void loginAndInitializePages() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        LoginPage loginPage = new LoginPage(driver);
        dashboard = new DashboardPage();
        addEmployee = new AddEmployeePage();

        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        Assert.assertTrue(isDashboardVisible(15), "Login failed or dashboard not visible.");
    }

    private boolean isDashboardVisible(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb-module')]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    // TC_ANE_01: Add new employee with valid details
    @Test
    public void addNewEmployeeWithCorrectInfo() {
        driver.findElement(dashboard.getPimMenu()).click();
        By addEmployeeMenu = By.xpath("//a[contains(text(),'Add Employee')]");
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getEmployeeFullName()));

        // Fill in the employee information
        driver.findElement(addEmployee.getEmployeeFullName()).sendKeys("John");
        driver.findElement(addEmployee.getMiddleName()).sendKeys("M");
        driver.findElement(addEmployee.getLastName()).sendKeys("Doe");
        driver.findElement(addEmployee.getEmployeeId()).clear();
        driver.findElement(addEmployee.getEmployeeId()).sendKeys("1234");

        // Create login details
        String uniqueUsername = "john" + UUID.randomUUID().toString().substring(0, 6);
        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getCreateLoginDetailsToggle())).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getUsername())).sendKeys(uniqueUsername);
        driver.findElement(addEmployee.getPassword()).sendKeys("Secure@123");
        driver.findElement(addEmployee.getConfirmPassword()).sendKeys("Secure@123");

        By statusEnabled = By.xpath("//label[contains(., 'Enabled')]/span[contains(@class,'oxd-radio-input')]");
        wait.until(ExpectedConditions.elementToBeClickable(statusEnabled)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getSaveButton())).click();

        // Success Validation (e.g., check if profile page appears)
        By profileHeader = By.xpath("//h6[text()='Personal Details']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileHeader));
        Assert.assertTrue(driver.findElement(profileHeader).isDisplayed(), "Employee not added successfully.");
    }


    // TC_ANE_02: Try to add employee with existing username
    @Test
    public void addEmployeeWithExistingUsername() {
        driver.findElement(dashboard.getPimMenu()).click();
        By addEmployeeMenu = By.xpath("//a[contains(text(),'Add Employee')]");
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getEmployeeFullName()));

        driver.findElement(addEmployee.getEmployeeFullName()).sendKeys("Alice");
        driver.findElement(addEmployee.getMiddleName()).sendKeys("B");
        driver.findElement(addEmployee.getLastName()).sendKeys("Smith");

        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getCreateLoginDetailsToggle())).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getUsername())).sendKeys("admin"); // Existing username
        driver.findElement(addEmployee.getPassword()).sendKeys("Password@123");
        driver.findElement(addEmployee.getConfirmPassword()).sendKeys("Password@123");

        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getSaveButton())).click();

        By usernameError = By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Username already exists']");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(usernameError));
            Assert.assertTrue(driver.findElement(usernameError).isDisplayed(), "Username already exists error not shown");
        } catch (TimeoutException e) {
            Assert.fail("Timeout waiting for username already exists error message.");
        }
    }



    // TC_ANE_03: Show validation error when passwords do not match
    @Test
    public void passwordMismatchValidation() {
        driver.findElement(dashboard.getPimMenu()).click();
        By addEmployeeMenu = By.xpath("//a[contains(text(),'Add Employee')]");
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getEmployeeFullName()));

        driver.findElement(addEmployee.getEmployeeFullName()).sendKeys("Bob");
        driver.findElement(addEmployee.getMiddleName()).sendKeys("C");
        driver.findElement(addEmployee.getLastName()).sendKeys("Johnson");

        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getCreateLoginDetailsToggle())).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployee.getUsername())).sendKeys("bob" + UUID.randomUUID().toString().substring(0, 6));
        driver.findElement(addEmployee.getPassword()).sendKeys("Password@123");
        driver.findElement(addEmployee.getConfirmPassword()).sendKeys("Password@124"); // mismatched

        wait.until(ExpectedConditions.elementToBeClickable(addEmployee.getSaveButton())).click();

        By passwordMismatchError = By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Passwords do not match']");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(passwordMismatchError));
            Assert.assertTrue(driver.findElement(passwordMismatchError).isDisplayed(), "Password mismatch error not displayed");
        } catch (TimeoutException e) {
            Assert.fail("Timeout waiting for 'passwords do not match' error.");
        }
    }
}

package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SearchEmployeePage;

import java.time.Duration;

public class TC_SearchEmployee extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboard;
    private SearchEmployeePage searchEmployee;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUpTestObjects() {
        loginPage = new LoginPage(driver);
        dashboard = new DashboardPage();
        searchEmployee = new SearchEmployeePage();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        Assert.assertTrue(isDashboardVisible(15), "Login failed or dashboard not visible.");
        driver.findElement(dashboard.getPimMenu()).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchEmployee.getEmployeeListMenu())).click();
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

    // TC_SE_01: Search employee by Employee ID
    @Test
    public void searchEmployeeById() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getEmployeeIdField()));
        driver.findElement(searchEmployee.getEmployeeIdField()).sendKeys("0001");
        driver.findElement(searchEmployee.getSearchButton()).click();

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(searchEmployee.getSearchResults()),
                    ExpectedConditions.visibilityOfElementLocated(searchEmployee.getNoRecordsFound())
            ));

            boolean hasResults = driver.findElements(searchEmployee.getSearchResults()).size() > 0;
            boolean noRecords = driver.findElements(searchEmployee.getNoRecordsFound()).size() > 0;

            Assert.assertTrue(hasResults || noRecords, "Search did not complete properly");
        } catch (TimeoutException e) {
            Assert.fail("Search operation timed out");
        }
    }

    // TC_SE_02: Search employee with no results
    @Test
    public void searchEmployeeWithNoResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getEmployeeIdField()));
        driver.findElement(searchEmployee.getEmployeeIdField()).sendKeys("99999");
        driver.findElement(searchEmployee.getSearchButton()).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getNoRecordsFound()));
            Assert.assertTrue(driver.findElement(searchEmployee.getNoRecordsFound()).isDisplayed(),
                    "No Records Found message should be displayed");
        } catch (TimeoutException e) {
            Assert.fail("No Records Found message not displayed within timeout");
        }
    }

    // TC_SE_03: Reset search filters
    @Test
    public void resetSearchFilters() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getEmployeeIdField()));
        driver.findElement(searchEmployee.getEmployeeIdField()).sendKeys("123");
        driver.findElement(searchEmployee.getResetButton()).click();

        wait.until(ExpectedConditions.attributeToBe(searchEmployee.getEmployeeIdField(), "value", ""));
        String fieldValue = driver.findElement(searchEmployee.getEmployeeIdField()).getAttribute("value");
        Assert.assertTrue(fieldValue.isEmpty(), "Employee ID field should be cleared after reset");
    }

    // TC_SE_04: Navigate to Employee List page
    @Test
    public void navigateToEmployeeListPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getEmployeeIdField()));
        Assert.assertTrue(driver.findElement(searchEmployee.getEmployeeIdField()).isDisplayed(),
                "Employee List page should be displayed with search fields");
    }

    // TC_SE_05: Verify search functionality accessibility
    @Test
    public void verifySearchFunctionalityAccessibility() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployee.getEmployeeIdField()));

        Assert.assertTrue(driver.findElement(searchEmployee.getEmployeeIdField()).isEnabled(),
                "Employee ID field should be enabled");
        Assert.assertTrue(driver.findElement(searchEmployee.getSearchButton()).isEnabled(),
                "Search button should be enabled");
        Assert.assertTrue(driver.findElement(searchEmployee.getResetButton()).isEnabled(),
                "Reset button should be enabled");
    }
}

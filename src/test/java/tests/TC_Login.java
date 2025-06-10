package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class TC_Login extends BaseTest {

    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private boolean isDashboardVisible(int timeoutSeconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Dashboard')]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isDashboardNotVisible() {
        try {
            return !loginPage.getDashboardHeading().isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    // TC_Login_01: Valid login
    @Test
    public void testValidLogin() {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        Assert.assertTrue(dashboard.isDisplayed(), "Login should be successful with valid credentials.");
    }

    // TC_Login_02: Wrong password
    @Test
    public void testLoginWithWrongPassword() {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLogin();

        Assert.assertFalse(isDashboardVisible(3), "Dashboard should NOT be visible after wrong password.");
    }

    // TC_Login_03: Wrong username
    @Test
    public void testLoginWithWrongUsername() {
        loginPage.enterUsername("WrongUser");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();

        wait.until(driver -> isDashboardNotVisible());
        Assert.assertTrue(isDashboardNotVisible(), "Dashboard should NOT be visible after wrong username.");
    }

    // TC_Login_04: Invalid username and password
    @Test
    public void testLoginWithInvalidCredentials() {
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("invalidPass");
        loginPage.clickLogin();

        wait.until(driver -> isDashboardNotVisible());
        Assert.assertTrue(isDashboardNotVisible(), "Dashboard should NOT be visible with invalid credentials.");
    }

    // TC_Login_05: Empty credentials
    @Test
    public void testLoginWithEmptyCredentials() {
        loginPage.clickLogin();
        wait.until(driver -> loginPage.isRequiredFieldErrorVisible());

        Assert.assertTrue(loginPage.isRequiredFieldErrorVisible(), "Required field error should appear for empty login.");
    }

    // TC_Login_06: Whitespace in credentials
    @Test
    public void testLoginWithWhitespaceInCredentials() {
        loginPage.enterUsername(" Admin ");
        loginPage.enterPassword(" admin123 ");
        loginPage.clickLogin();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Dashboard')]")));
            Assert.fail("Login should not succeed with credentials containing leading/trailing spaces.");
        } catch (TimeoutException e) {
            Assert.assertTrue(true); // Expected
        }
    }

    // TC_Login_07: SQL Injection attempt
    @Test
    public void testLoginWithSQLInjectionAttempt() {
        loginPage.enterUsername("' OR '1'='1");
        loginPage.enterPassword("' OR '1'='1");
        loginPage.clickLogin();

        wait.until(driver -> isDashboardNotVisible());
        Assert.assertTrue(isDashboardNotVisible(), "SQL injection attempt should NOT allow login.");
    }

    // TC_Login_08: XSS attack attempt
    @Test
    public void testLoginWithXSSAttempt() {
        loginPage.enterUsername("<script>alert('XSS')</script>");
        loginPage.enterPassword("irrelevant");
        loginPage.clickLogin();

        wait.until(driver -> isDashboardNotVisible());
        Assert.assertTrue(isDashboardNotVisible(), "XSS attack input should NOT allow login.");
    }
}

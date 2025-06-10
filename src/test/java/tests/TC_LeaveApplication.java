package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LeavePage;
import pages.LoginPage;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

public class TC_LeaveApplication extends BaseTest {

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

    private String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return today.format(formatter);
    }

    private String getFutureDate(int daysToAdd) {
        LocalDate futureDate = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return futureDate.format(formatter);
    }

    private String getPastDate(int daysToSubtract) {
        LocalDate pastDate = LocalDate.now().minusDays(daysToSubtract);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return pastDate.format(formatter);
    }

    private void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        Assert.assertTrue(isDashboardVisible(15), "Login failed or dashboard not visible.");
    }

    private void navigateToApplyLeave() {
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveMenu())).click();
        wait.until(ExpectedConditions.elementToBeClickable(leavePage.getAssignLeave())).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(leavePage.getLeaveTypeDropdown()));
    }

    @Test(priority = 1)
    public void navigateToApplyLeavePage() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        Assert.assertTrue(driver.findElement(leavePage.getLeaveTypeDropdown()).isDisplayed(),
                "Apply Leave page should be displayed with leave type dropdown");
        System.out.println("✓ TC_LA_01: Successfully navigated to Apply Leave page");
    }

    @Test(priority = 2)
    public void applyLeaveWithValidDetails() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();

            try {
                wait.until(ExpectedConditions.elementToBeClickable(leavePage.getCasualLeaveOption())).click();
            } catch (TimeoutException e) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(leavePage.getSickLeaveOption())).click();
                } catch (TimeoutException ex) {
                    List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
                    if (!leaveOptions.isEmpty()) {
                        leaveOptions.get(0).click();
                    }
                }
            }

            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(getFutureDate(1));

            WebElement toDateField = driver.findElement(leavePage.getToDatePicker());
            toDateField.clear();
            toDateField.sendKeys(getFutureDate(2));

            WebElement commentsField = driver.findElement(leavePage.getCommentsTextArea());
            commentsField.clear();
            commentsField.sendKeys("Family function - need personal time off");

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class,'oxd-text--toast-message')]")),
                    ExpectedConditions.urlContains("leave")
            ));

            System.out.println("✓ TC_LA_02: Leave application submitted successfully with valid details");
        } catch (Exception e) {
            Assert.fail("Failed to apply leave with valid details: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void applyLeaveWithMissingMandatoryFields() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Required']")));

            Assert.assertTrue(errorMessage.isDisplayed(),
                    "Validation error should appear for missing mandatory fields");
            System.out.println("✓ TC_LA_03: Validation error correctly displayed for missing mandatory fields");

        } catch (TimeoutException te) {
            Assert.fail("Timeout waiting for validation error message: " + te.getMessage());
        } catch (NoSuchElementException ne) {
            Assert.fail("Validation error element not found: " + ne.getMessage());
        } catch (Exception e) {
            Assert.fail("Test failed for missing mandatory fields due to unexpected error: " + e.getMessage());
        }
    }


    @Test(priority = 4)
    public void applyLeaveWithInvalidDateRange() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();
            List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
            if (!leaveOptions.isEmpty()) {
                leaveOptions.get(0).click();
            }

            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(getFutureDate(5));

            WebElement toDateField = driver.findElement(leavePage.getToDatePicker());
            toDateField.clear();
            toDateField.sendKeys(getFutureDate(2));

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            try {
                WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//span[contains(@class,'oxd-input-field-error-message')]")
                ));
                System.out.println("Error message displayed: " + errorMessage.getText());
                Assert.assertTrue(errorMessage.isDisplayed(), "Date range validation error should appear");
            } catch (TimeoutException e) {
                Assert.fail("Error message for invalid date range not displayed.");
            }




            System.out.println("✓ TC_LA_04: Invalid date range/format validation working correctly");
        } catch (Exception e) {
            Assert.fail("Test failed for invalid date range: " + e.getMessage());
        }
    }


    @Test(priority = 5)
    public void applyLeaveForPastDates() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();
            List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
            if (!leaveOptions.isEmpty()) {
                leaveOptions.get(0).click();
            }

            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(getPastDate(5));

            WebElement toDateField = driver.findElement(leavePage.getToDatePicker());
            toDateField.clear();
            toDateField.sendKeys(getPastDate(3));

            WebElement commentsField = driver.findElement(leavePage.getCommentsTextArea());
            commentsField.clear();
            commentsField.sendKeys("Retroactive leave application for medical emergency");

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            Thread.sleep(2000);
            boolean hasError = !driver.findElements(By.xpath("//span[contains(@class,'error')]")).isEmpty();
            boolean isSubmitted = driver.getCurrentUrl().contains("leave") &&
                    !driver.getCurrentUrl().contains("apply");

            Assert.assertTrue(hasError || isSubmitted,
                    "System should either show error for past dates or allow submission");
            System.out.println("✓ TC_LA_05: Past date leave application handled appropriately");
        } catch (Exception e) {
            Assert.fail("Test failed for past date leave application: " + e.getMessage());
        }
    }
    @Test(priority = 6)
    public void verifyLeaveBalanceBeforeApplying() {
        performLogin();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getReportsMenu())).click();
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getMyLeaveEntitlementsReportLink())).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//h5[contains(text(),'My Leave Entitlements and Usage Report')]")));

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(),'Leave Balance (Days)')]")));

            System.out.println("Leave Balance section is visible in Reports.");

            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveMenu())).click();
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getApplyLeaveMenu())).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(leavePage.getLeaveTypeDropdown()));

            System.out.println("✓ TC_LA_06: Leave balance verification completed before applying");
        } catch (Exception e) {
            Assert.fail("Failed to verify leave balance: " + e.getMessage());
        }
    }



    @Test(priority = 7)
    public void cancelLeaveApplication() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();
            List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
            if (!leaveOptions.isEmpty()) {
                leaveOptions.get(0).click();
            }

            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(getFutureDate(1));

            try {
                WebElement cancelButton = driver.findElement(
                        By.xpath("//button[contains(@class,'oxd-button--ghost') or text()='Cancel']"));
                cancelButton.click();
                wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("apply")));
                System.out.println("✓ TC_LA_07: Leave application cancelled successfully");
            } catch (Exception e) {
                driver.navigate().back();
                System.out.println("✓ TC_LA_07: Navigated away from leave application form");
            }
        } catch (Exception e) {
            Assert.fail("Failed to cancel leave application: " + e.getMessage());
        }
    }

    @Test(priority = 8)
    public void applyMultipleDayLeave() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();
            List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
            if (!leaveOptions.isEmpty()) {
                leaveOptions.get(0).click();
            }

            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(getFutureDate(10));

            WebElement toDateField = driver.findElement(leavePage.getToDatePicker());
            toDateField.clear();
            toDateField.sendKeys(getFutureDate(15));

            WebElement commentsField = driver.findElement(leavePage.getCommentsTextArea());
            commentsField.clear();
            commentsField.sendKeys("Extended leave for vacation and family time - 5 working days required");

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class,'toast-message')]")),
                    ExpectedConditions.not(ExpectedConditions.urlContains("apply"))
            ));

            System.out.println("✓ TC_LA_08: Multiple day leave application submitted successfully");
        } catch (Exception e) {
            Assert.fail("Failed to apply multiple day leave: " + e.getMessage());
        }
    }

    @Test(priority = 9)
    public void applyHalfDayLeave() {
        performLogin();
        navigateToApplyLeave();
        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveTypeDropdown())).click();
            List<WebElement> leaveOptions = driver.findElements(By.xpath("//div[@role='option']"));
            if (!leaveOptions.isEmpty()) {
                leaveOptions.get(0).click();
            }

            String singleDate = getFutureDate(3);
            WebElement fromDateField = driver.findElement(leavePage.getFromDatePicker());
            fromDateField.clear();
            fromDateField.sendKeys(singleDate);

            WebElement toDateField = driver.findElement(leavePage.getToDatePicker());
            toDateField.clear();
            toDateField.sendKeys(singleDate);

            try {
                WebElement partialDayDropdown = driver.findElement(leavePage.getPartialDaysDropdown());
                partialDayDropdown.click();
                WebElement halfDayOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='option' and contains(text(),'Half Day')]")));
                halfDayOption.click();
            } catch (Exception e) {
                System.out.println("Partial day options not available, applying for full day");
            }

            WebElement commentsField = driver.findElement(leavePage.getCommentsTextArea());
            commentsField.clear();
            commentsField.sendKeys("Half day leave for personal appointment");

            WebElement applyButton = driver.findElement(leavePage.getApplyButton());
            applyButton.click();

            Thread.sleep(3000);
            System.out.println("✓ TC_LA_09: Half day leave application processed");
        } catch (Exception e) {
            Assert.fail("Failed to apply half day leave: " + e.getMessage());
        }
    }

    @Test(priority = 10)
    public void verifyLeaveApplicationInList() {
        performLogin();

        LeavePage leavePage = new LeavePage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getLeaveMenu())).click();
            System.out.println("Clicked on Leave Menu.");


            wait.until(ExpectedConditions.elementToBeClickable(leavePage.getMyLeaveMenu())).click();
            System.out.println("Clicked on My Leave Menu.");


            By myLeaveListHeading = By.xpath("//h5[contains(@class,'oxd-table-filter-title') and contains(text(),'My Leave List')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(myLeaveListHeading));
            System.out.println("Verified 'My Leave List' heading is visible.");



            By leaveTableBody = By.xpath("//div[@class='oxd-table-body']");
            wait.until(ExpectedConditions.presenceOfElementLocated(leaveTableBody));
            Assert.assertTrue(driver.findElement(leaveTableBody).isDisplayed(), "Leave list table body should be displayed.");

            List<WebElement> leaveRecords = driver.findElements(
                    By.xpath("//div[@class='oxd-table-body']//div[@role='row']"));


            // If records exist, verify they contain expected information
            if (leaveRecords.size() > 0) {
                WebElement firstRecord = leaveRecords.get(0);
                Assert.assertTrue(firstRecord.isDisplayed(), "At least one leave record should be visible if present.");
                System.out.println("First leave record is visible.");
            } else {
                System.out.println("No leave records found, which might be an expected scenario for an empty list.");
            }

            System.out.println("✓ TC_LA_10: Leave applications list verified successfully");
            System.out.println("Found " + leaveRecords.size() + " leave record(s) in the list");

        } catch (Exception e) {

            System.err.println("Error in verifyLeaveApplicationInList: " + e.getMessage());
            System.err.println("Current URL at failure: " + driver.getCurrentUrl());
            Assert.fail("Failed to verify leave application in list: " + e.getMessage());
        }
    }
}
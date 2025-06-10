package pages;

import org.openqa.selenium.By;

public class LeavePage {

    private By leaveMenu = By.xpath("//span[text()='Leave']");
    private By applyLeaveMenu = By.xpath("//a[contains(text(),'Apply')]");
    private By myLeaveMenu = By.xpath("//a[contains(text(),'My Leave')]");
    private By leaveListMenu = By.xpath("//a[contains(text(),'Leave List')]");

    // Apply Leave form fields
    private By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/following::div[1]//div[@class='oxd-select-text-input']");
    private By fromDatePicker = By.xpath("//label[text()='From Date']/following::input[1]");
    private By toDatePicker = By.xpath("//label[text()='To Date']/following::input[1]");
    private By partialDaysDropdown = By.xpath("//label[text()='Partial Days']/following::div[1]//div[@class='oxd-select-text-input']");
    private By durationDropdown = By.xpath("//label[text()='Duration']/following::div[1]//div[@class='oxd-select-text-input']");
    private By commentsTextArea = By.xpath("//label[text()='Comments']/following::textarea[1]");

    // Buttons
    private By applyButton = By.xpath("//button[@type='submit']");
    private By cancelButton = By.xpath("//button[text()=' Cancel ']");

    // Leave balance
    private By leaveBalanceText = By.xpath("//p[contains(text(),'Balance')]");

    // Success/Error messages
    private By successMessage = By.xpath("//div[contains(@class,'oxd-toast-content--success')]");
    private By errorMessage = By.xpath("//div[contains(@class,'oxd-toast-content--error')]");

    // Leave list filters
    private By fromDateFilter = By.xpath("//label[text()='From Date']/following::input[1]");
    private By toDateFilter = By.xpath("//label[text()='To Date']/following::input[1]");
    private By employeeNameFilter = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By leaveStatusFilter = By.xpath("//label[text()='Leave Status']/following::div[1]//div[@class='oxd-select-text-input']");
    private By leaveTypeFilter = By.xpath("//label[text()='Leave Type']/following::div[1]//div[@class='oxd-select-text-input']");

    private By searchButton = By.xpath("//button[@type='submit']");
    private By resetButton = By.xpath("//button[text()=' Reset ']");

    // Leave list results
    private By leaveListTable = By.xpath("//div[@class='oxd-table-body']");
    private By noRecordsFound = By.xpath("//span[text()='No Records Found']");

    // Assign Leave
    private By AssignLeave = By.xpath("//a[contains(text(),'Assign Leave')]");


    // Common dropdown options
    private By casualLeaveOption = By.xpath("//span[text()='CAN - FMLA']");
    private By sickLeaveOption = By.xpath("//span[text()='CAN - Personal']");

    private By reportsMenu = By.xpath("//span[contains(text(),'Reports')]");
    private By myLeaveEntitlementsReportLink = By.xpath("//a[contains(text(),'My Leave Entitlements and Usage Report')]");


    // Getters
    public By getLeaveMenu() { return leaveMenu; }
    public By getApplyLeaveMenu() { return applyLeaveMenu; }
    public By getMyLeaveMenu() { return myLeaveMenu; }
    public By getLeaveListMenu() { return leaveListMenu; }
    public By getLeaveTypeDropdown() { return leaveTypeDropdown; }
    public By getFromDatePicker() { return fromDatePicker; }
    public By getToDatePicker() { return toDatePicker; }
    public By getPartialDaysDropdown() { return partialDaysDropdown; }
    public By getDurationDropdown() { return durationDropdown; }
    public By getCommentsTextArea() { return commentsTextArea; }
    public By getApplyButton() { return applyButton; }
    public By getCancelButton() { return cancelButton; }
    public By getLeaveBalanceText() { return leaveBalanceText; }
    public By getSuccessMessage() { return successMessage; }
    public By getErrorMessage() { return errorMessage; }
    public By getFromDateFilter() { return fromDateFilter; }
    public By getToDateFilter() { return toDateFilter; }
    public By getEmployeeNameFilter() { return employeeNameFilter; }
    public By getLeaveStatusFilter() { return leaveStatusFilter; }
    public By getLeaveTypeFilter() { return leaveTypeFilter; }
    public By getSearchButton() { return searchButton; }
    public By getResetButton() { return resetButton; }
    public By getLeaveListTable() { return leaveListTable; }
    public By getNoRecordsFound() { return noRecordsFound; }
    public By getCasualLeaveOption() { return casualLeaveOption; }
    public By getSickLeaveOption() { return sickLeaveOption; }
    public By getAssignLeave() {return AssignLeave;}
    public By getReportsMenu() { return reportsMenu; }
    public By getMyLeaveEntitlementsReportLink() { return myLeaveEntitlementsReportLink; }

}
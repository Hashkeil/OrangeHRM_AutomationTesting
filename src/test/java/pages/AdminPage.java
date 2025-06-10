package pages;

import org.openqa.selenium.By;

public class AdminPage {

    private By adminMenu = By.xpath("//span[text()='Admin']");
    private By userManagementMenu = By.xpath("//span[text()='User Management ']");
    private By usersSubmenu = By.xpath("//a[contains(text(),'Users')]");

    // Add User button
    private By addUserButton = By.xpath("//button[text()=' Add ']");


    // User search filters
    private By usernameFilter = By.xpath("//label[text()='Username']/following::input[1]");
    private By userRoleFilter = By.xpath("//label[text()='User Role']/following::div[1]//div[@class='oxd-select-text-input']");
    private By employeeNameFilter = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By statusFilter = By.xpath("//label[text()='Status']/following::div[1]//div[@class='oxd-select-text-input']");

    // Search and Reset buttons
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resetButton = By.xpath("//button[text()=' Reset ']");

    // Add User form fields
    private By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[1]//div[@class='oxd-select-text-input']");
    private By employeeNameField = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[1]//div[@class='oxd-select-text-input']");
    private By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    private By passwordField = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/following::input[1]");

    // Form buttons
    private By saveButton = By.xpath("//button[@type='submit']");
    private By cancelButton = By.xpath("//button[text()=' Cancel ']");

    // User list table
    private By userListTable = By.xpath("//div[@class='oxd-table-body']");
    private By noRecordsFound = By.xpath("//span[text()='No Records Found']");
    private By recordsFoundText = By.xpath("//span[contains(text(),'Records Found')]");

    // Dropdown options
    private By adminRoleOption = By.xpath("//span[text()='Admin']");
    private By essRoleOption = By.xpath("//span[text()='ESS']");
    private By enabledStatusOption = By.xpath("//span[text()='Enabled']");
    private By disabledStatusOption = By.xpath("//span[text()='Disabled']");

    // Success/Error messages
    private By successMessage = By.xpath("//div[contains(@class,'oxd-toast-content--success')]");
    private By errorMessage = By.xpath("//div[contains(@class,'oxd-toast-content--error')]");
    private By usernameExistsError = By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Username already exists']");
    private By passwordMismatchError = By.xpath("//span[contains(@class,'oxd-input-field-error-message') and contains(text(),'match')]");

    // User actions
    private By editUserIcon = By.xpath("//i[contains(@class,'bi-pencil-fill')]");
    private By deleteUserIcon = By.xpath("//i[contains(@class,'bi-trash')]");

    // Organization section
    private By organizationMenu = By.xpath("//span[text()='Organization ']");
    private By generalInformationSubmenu = By.xpath("//a[contains(text(),'General Information')]");
    private By locationsSubmenu = By.xpath("//a[contains(text(),'Locations')]");
    private By structureSubmenu = By.xpath("//a[contains(text(),'Structure')]");

    // Job section
    private By jobMenu = By.xpath("//span[text()='Job ']");
    private By jobTitlesSubmenu = By.xpath("//a[contains(text(),'Job Titles')]");
    private By paySalesSubmenu = By.xpath("//a[contains(text(),'Pay Grades')]");
    private By employmentStatusSubmenu = By.xpath("//a[contains(text(),'Employment Status')]");


    public By getAdminMenu() { return adminMenu; }
    public By getUserManagementMenu() { return userManagementMenu; }
    public By getUsersSubmenu() { return usersSubmenu; }
    public By getAddUserButton() { return addUserButton; }
    public By getUsernameFilter() { return usernameFilter; }
    public By getUserRoleFilter() { return userRoleFilter; }
    public By getEmployeeNameFilter() { return employeeNameFilter; }
    public By getStatusFilter() { return statusFilter; }
    public By getSearchButton() { return searchButton; }
    public By getResetButton() { return resetButton; }
    public By getUserRoleDropdown() { return userRoleDropdown; }
    public By getEmployeeNameField() { return employeeNameField; }
    public By getStatusDropdown() { return statusDropdown; }
    public By getUsernameField() { return usernameField; }
    public By getPasswordField() { return passwordField; }
    public By getConfirmPasswordField() { return confirmPasswordField; }
    public By getSaveButton() { return saveButton; }
    public By getCancelButton() { return cancelButton; }
    public By getUserListTable() { return userListTable; }
    public By getNoRecordsFound() { return noRecordsFound; }
    public By getRecordsFoundText() { return recordsFoundText; }
    public By getAdminRoleOption() { return adminRoleOption; }
    public By getEssRoleOption() { return essRoleOption; }
    public By getEnabledStatusOption() { return enabledStatusOption; }
    public By getDisabledStatusOption() { return disabledStatusOption; }
    public By getSuccessMessage() { return successMessage; }
    public By getErrorMessage() { return errorMessage; }
    public By getUsernameExistsError() { return usernameExistsError; }
    public By getPasswordMismatchError() { return passwordMismatchError; }
    public By getEditUserIcon() { return editUserIcon; }
    public By getDeleteUserIcon() { return deleteUserIcon; }
}
package pages;

import org.openqa.selenium.By;

public class SearchEmployeePage {

    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By employeeListMenu = By.xpath("//a[contains(text(),'Employee List')]");

    // Search fields
    private By employeeNameField = By.xpath("//label[text()='Employee Name']/following::input[1]");
    private By employeeIdField = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private By employmentStatusDropdown = By.xpath("//label[text()='Employment Status']/following::div[1]//div[@class='oxd-select-text-input']");
    private By includeDropdown = By.xpath("//label[text()='Include']/following::div[1]//div[@class='oxd-select-text-input']");
    private By supervisorNameField = By.xpath("//label[text()='Supervisor Name']/following::input[1]");
    private By jobTitleDropdown = By.xpath("//label[text()='Job Title']/following::div[1]//div[@class='oxd-select-text-input']");
    private By subUnitDropdown = By.xpath("//label[text()='Sub Unit']/following::div[1]//div[@class='oxd-select-text-input']");

    // Action buttons
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resetButton = By.xpath("//button[text()=' Reset ']");

    // Results
    private By searchResults = By.xpath("//div[@class='oxd-table-body']");
    private By noRecordsFound = By.xpath("//span[text()='No Records Found']");
    private By recordsFoundText = By.xpath("//span[contains(text(),'Records Found')]");
    private By firstEmployeeRecord = By.xpath("//div[@class='oxd-table-body']//div[@class='oxd-table-row'][1]");

    // Employee list table headers
    private By employeeIdHeader = By.xpath("//div[text()='Id']");
    private By firstLastNameHeader = By.xpath("//div[text()='First (& Middle) Name']");
    private By employeeLastNameHeader = By.xpath("//div[text()='Last Name']");
    private By jobTitleHeader = By.xpath("//div[text()='Job Title']");
    private By employmentStatusHeader = By.xpath("//div[text()='Employment Status']");
    private By subUnitHeader = By.xpath("//div[text()='Sub Unit']");
    private By supervisorHeader = By.xpath("//div[text()='Supervisor']");
    private By actionsHeader = By.xpath("//div[text()='Actions']");

    // Getters
    public By getPimMenu() { return pimMenu; }
    public By getEmployeeListMenu() { return employeeListMenu; }
    public By getEmployeeNameField() { return employeeNameField; }
    public By getEmployeeIdField() { return employeeIdField; }
    public By getEmploymentStatusDropdown() { return employmentStatusDropdown; }
    public By getIncludeDropdown() { return includeDropdown; }
    public By getSupervisorNameField() { return supervisorNameField; }
    public By getJobTitleDropdown() { return jobTitleDropdown; }
    public By getSubUnitDropdown() { return subUnitDropdown; }
    public By getSearchButton() { return searchButton; }
    public By getResetButton() { return resetButton; }
    public By getSearchResults() { return searchResults; }
    public By getNoRecordsFound() { return noRecordsFound; }
    public By getRecordsFoundText() { return recordsFoundText; }
    public By getFirstEmployeeRecord() { return firstEmployeeRecord; }
}
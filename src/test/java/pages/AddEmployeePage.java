package pages;

import org.openqa.selenium.By;

public class AddEmployeePage {


    private By pimMenu = By.xpath("//span[text()='PIM']");

    private By employeeFullName = By.xpath("//input[@name = 'firstName']");
    private By middleName = By.xpath("//input[@name = 'middleName']");
    private By lastName = By.xpath("//input[@name = 'lastName']");
    private By employeeId = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private By profilePictureUpload = By.xpath("//input[@type='file']");

    private By createLoginDetailsToggle = By.xpath("//span[contains(@class, 'oxd-switch-input')]");


    private By username = By.xpath("//label[text()='Username']/following::input[1]");
    private By statusEnabled = By.xpath("//label[contains(., 'Enabled')]/input[@type='radio' and @value='1']");
    private By statusDisabled = By.xpath("//label[contains(., 'Disabled')]/input[@type='radio' and @value='2']");


    private By password = By.xpath("//label[text()='Password']/following::input[1]");
    private By confirmPassword = By.xpath("//label[text()='Confirm Password']/following::input[1]");


    private By saveButton = By.xpath("//button[@type='submit']");
    private By cancelButton = By.xpath("//button[text()='Cancel']");


    public By getEmployeeFullName() {
        return employeeFullName;
    }
    public By getPimMenu() {
        return pimMenu;
    }
    public By getMiddleName() {
        return middleName;
    }
    public By getLastName() {
        return lastName;
    }
    public By getEmployeeId() {
        return employeeId;
    }
    public By getProfilePictureUpload() {
        return profilePictureUpload;
    }
    public By getCreateLoginDetailsToggle() {
        return createLoginDetailsToggle;
    }
    public By getUsername() {
        return username;
    }
    public By getStatusEnabled() {return statusEnabled;}
    public By getStatusDisabled() {
        return statusDisabled;
    }
    public By getPassword() {return password;}
    public By getConfirmPassword() {return confirmPassword;}
    public By getSaveButton() {return saveButton;}
    public By getCancelButton() {return cancelButton;}
}

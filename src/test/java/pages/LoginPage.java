package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;


    //Login
    private By usernameField = By.xpath("//input[@name = 'username']");
    private By passwordField = By.xpath("//input[@name = 'password']");
    private By loginButton = By.xpath("//button[text()=' Login ']");
    private By dashboardHeading = By.xpath("//h6[text()='Dashboard']");
    private By forgotYourPassword = By.xpath("//p[contains(text(),'Forgot your password?')]");
    private By requiredFieldError = By.xpath("//span[contains(@class, 'oxd-input-field-error-message') and text()='Required']");


    public LoginPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));

    }


    public void enterUsername(String username) {driver.findElement(usernameField).sendKeys(username);}
    public void enterPassword(String password) {driver.findElement(passwordField).sendKeys(password);}
    public void clickLogin() {driver.findElement(loginButton).click();}
    public WebElement getDashboardHeading() {return driver.findElement(dashboardHeading);}
    public WebElement getForgotYourPassword() {return driver.findElement(forgotYourPassword);}



    public boolean isRequiredFieldErrorVisible() {
        try {
            return driver.findElement(requiredFieldError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;
    By useNameTextBox = By.id("user-name");
    By passwordTextBox = By.id("password");
    By loginButton = By.id("login-button");
    By passwordRequiredText = By.xpath("//div[@class='error-message-container error']/h3");
    By useNameRequiredText = By.xpath("//div[@class='error-message-container error']/h3");
    By invalidUserNameOrPasswrdText = By.xpath("//div[@class='error-message-container error']/h3");
    By lockedOutUserText = By.xpath("//div[@class='error-message-container error']/h3");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    public Boolean getLoginButton() {
        return driver.findElement(loginButton).isDisplayed();
    }
    public LoginPage enterUserName(String userName){
        driver.findElement(useNameTextBox).sendKeys(userName);
        return this;
    }

    public LoginPage enterPassword(String password){
        driver.findElement(passwordTextBox).sendKeys(password);
        return this;
    }

    public LoginPage clickLoginButton(){
        driver.findElement(loginButton).click();
        return this;
    }

    public ProductPage successfulLogin(String userName,String password){
        enterUserName(userName);
        enterPassword(password);
        clickLoginButton();
        return new ProductPage(driver);
    }

    public LoginPage unsuccessfulLogin(String userName,String password){
        enterUserName(userName);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    public String getPasswordRequiredText() {
        return driver.findElement(passwordRequiredText).getText();
    }
    public String getUserNameRequiredText() {
        return driver.findElement(useNameRequiredText).getText();
    }
    public String getInvalidUserNameOrPasswordText() {return driver.findElement(invalidUserNameOrPasswrdText).getText();}
}

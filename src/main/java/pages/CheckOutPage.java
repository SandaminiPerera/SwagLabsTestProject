package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CheckOutPage {
    WebDriver driver;
    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
    }

    By yourInformationLabel = By.className("title");
    By firstNameTextBox = By.id("first-name");
    By lastNameTextBox = By.id("last-name");
    By zipCodeTextBox = By.id("postal-code");
    By continueButton = By.id("continue");
    By overviewLabel = By.className("title");
    By errorMessage = By.xpath("//div[@class='error-message-container error']/h3");
    By itemPrice = By.className("inventory_item_price");
    By subTotalLabel = By.className("summary_subtotal_label");
    By taxLabel = By.className("summary_tax_label");
    By toalLabel = By.className("summary_total_label");
    By finishButton =By.id("finish");
    By completeLabel =By.className("complete-header");

    public String getYourInformationLabel() {
        return driver.findElement(yourInformationLabel).getText();
    }
    public CheckOutPage enterFirstName(String firstName){

        driver.findElement(firstNameTextBox).sendKeys(firstName);
        return this;
    }
    public CheckOutPage enterLastName(String lastName){

        driver.findElement(lastNameTextBox).sendKeys(lastName);
        return this;
    }
    public CheckOutPage enterZipCode(String zipCode){

        driver.findElement(zipCodeTextBox).sendKeys(zipCode);
        return this;
    }
    public CheckOutPage enterYourInformation(String firstName,String lasName,String zipCode){
        enterFirstName(firstName);
        enterLastName(lasName);
        enterZipCode(zipCode);
        return this;
    }
    public CheckOutPage clickContinueButton(){

        driver.findElement(continueButton).click();
        return this;
    }
    public String getOverviewLabel() {
        return driver.findElement(overviewLabel).getText();
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
    public List<WebElement> itemPrice(){
        return driver.findElements(itemPrice);
    }
    public String getSubTotalPrice(){return driver.findElement(subTotalLabel).getText();}
    public String getTax(){return driver.findElement(taxLabel).getText();}
    public String getTotal(){return driver.findElement(toalLabel).getText();}
    public String getCompleteLabel(){return driver.findElement(completeLabel).getText();}
    public CheckOutPage clickFinish(){
        driver.findElement(finishButton).click();
        return this;
    }

}

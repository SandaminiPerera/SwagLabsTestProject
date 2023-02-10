package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    WebDriver driver;
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    By productName =By.className("inventory_item_name");
    By checkOutButton = By.id("checkout");

    public List<WebElement> getProductName() {
        return driver.findElements(productName);
    }
    public CheckOutPage clickCheckoutButton(){
        driver.findElement(checkOutButton).click();
        return new CheckOutPage(driver);
    }

}

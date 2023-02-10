package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }
    By productTitle = By.xpath("//div[@class='header_secondary_container']/span");
    By productPath =By.className("inventory_item");
    By productName =By.className("inventory_item_name");
    By productDescription =By.className("inventory_item_desc");
    By productPrice =By.className("inventory_item_price");
    By addCartButton =By.xpath("//div[@class='pricebar']/button");
    By shoppingCartText = By.className("shopping_cart_badge");
    By cartButton = By.className("shopping_cart_link");

    public List<WebElement> getProducts() {
        return driver.findElements(productPath);
    }
    public List<WebElement> getProductName() {
        return driver.findElements(productName);
    }
   /* public List<WebElement> getProductDescription() {
        return driver.findElements(productDescription);
    }
    public List<WebElement> getProductPrice() {
        return driver.findElements(productPrice);
    }*/
    public List<WebElement> getAddToCartButton() {
        return driver.findElements(addCartButton);
    }
    public String getProductTitle() {
        return driver.findElement(productTitle).getText();
    }
    public String getShoppingCartText() {
        return driver.findElement(shoppingCartText).getText();
    }

    public CartPage clickCart(){
         driver.findElement(cartButton).click();
         return new CartPage(driver);
    }
    public ProductPage addAnItemToCart(int i){
        getAddToCartButton().get(i).click();
        return this;
    }
    public int noOfAddToCartButtons(){
        return getAddToCartButton().size();
    }


}

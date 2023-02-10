import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.Configurations;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwagLabsTest {
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    Configurations configurations = new Configurations();


    @BeforeMethod
    public void setUp(){
        configurations.openBrowser();
        configurations.navigateToURL("https://www.saucedemo.com/");
        driver = Configurations.getDriver();

    }

    //---------------------LOGIN TEST----------------------------------------------------------------------
    @Test
    public void isLoginButtonPresence(){
        Boolean loginButtonPresence = new LoginPage(driver).getLoginButton();
        softAssert.assertEquals(loginButtonPresence,"true");
    }
    @Test
    public void successfulLogin(){

        new LoginPage(driver).successfulLogin("standard_user","secret_sauce");
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html","Error:Destination is incorrect");
    }

    @Test
    public void blankPassword(){
        String requiredPasswordText = new LoginPage(driver).unsuccessfulLogin("standard_user","")
                .getPasswordRequiredText();
        softAssert.assertEquals(requiredPasswordText.trim(),"Epic sadface: Password is required","Error: Password required text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void blankUserName(){
        String requiredUserNameText = new LoginPage(driver).unsuccessfulLogin("","secret_sauce")
                .getUserNameRequiredText();
        softAssert.assertEquals(requiredUserNameText.trim(),"Epic sadface: Username is required","Error: Username required text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void blankUserNameAndPassword(){
        String requiredUserNameText = new LoginPage(driver).unsuccessfulLogin("","").getUserNameRequiredText();
        softAssert.assertEquals(requiredUserNameText.trim(),"Epic sadface: Username is required","Error: Username required text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void invalidUserName(){
        String invalidUserNameOrPasswordText = new LoginPage(driver).unsuccessfulLogin("123456","secret_sauce").getInvalidUserNameOrPasswordText();
        softAssert.assertEquals(invalidUserNameOrPasswordText.trim(),"Epic sadface: Username and password do not match any user in this service","Error: Username invalid text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void invalidPassword(){
        String invalidUserNameOrPasswordText = new LoginPage(driver).unsuccessfulLogin("standard_user","123456").getInvalidUserNameOrPasswordText();
        softAssert.assertEquals(invalidUserNameOrPasswordText.trim(),"Epic sadface: Username and password do not match any user in this service","Error: Password invalid text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void invalidUserNameAndPassword(){
        String invalidUserNameOrPasswordText = new LoginPage(driver).unsuccessfulLogin("123456","123456").getInvalidUserNameOrPasswordText();
        softAssert.assertEquals(invalidUserNameOrPasswordText.trim(),"Epic sadface: Username and password do not match any user in this service","Error: Password invalid text is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void lockedOutUser(){
        String lockedOutUserText = new LoginPage(driver).unsuccessfulLogin("locked_out_user","secret_sauce").getUserNameRequiredText();
        softAssert.assertEquals(lockedOutUserText.trim(),"Epic sadface: Sorry, this user has been locked out.","Error: User has been locked out text is incorrect");
    }

    //-----------------------PRODUCT TEST---------------------------------------------------------

    @Test
    public void presenceOfProductTitle(){
        String productTitle = new LoginPage(driver).successfulLogin("standard_user","secret_sauce").getProductTitle();
        softAssert.assertEquals(productTitle.trim(),"PRODUCTS","Error: product title is incorrect");
    }

     @Test
    public void presenceOfElements(){
         List<WebElement> products = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                 .getProducts();
        softAssert.assertNotNull(products);
    }

    @Test
    public void validateAddAnItemToCart(){
        String shoppingCartBadge = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).getShoppingCartText();
          softAssert.assertEquals(shoppingCartBadge.trim(),"1","Error: Item is not added to Cart");
    }

    @Test
    public void validateAddMultipleItemToCart(){
        int size = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .noOfAddToCartButtons();
        int n = ThreadLocalRandom.current().nextInt(0,size-1);
        for(int i=0;i<=n;i++) {
            new ProductPage(driver).addAnItemToCart(i);
        }
        String shoppingCartBadge = new ProductPage(driver).getShoppingCartText();
        softAssert.assertEquals(shoppingCartBadge.trim(),n,"Error: Item is not added to Cart");
    }

    //-----------------------------CART TEST--------------------------------

    @Test
    public void validateCartForAnItem(){
        List<WebElement> productNameOnPage = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).getProductName();
        String productNameOnPageText = productNameOnPage.get(0).getText();
        List<WebElement> productNameOnCart = new CartPage(driver).getProductName();
        String productNameOnCartText = productNameOnCart.get(0).getText();
        softAssert.assertEquals(productNameOnPageText,productNameOnCartText,"Error: ProductName on the cart is incorrect");

    }

    @Test
    public void validateCartForMultipleItems(){
        int size = new LoginPage(driver).successfulLogin("standard_user","secret_sauce").noOfAddToCartButtons();
        int n = ThreadLocalRandom.current().nextInt(0,size-1);
        for(int i=0;i<=n;i++) {
            new ProductPage(driver).addAnItemToCart(i);
        }

        for(int j =0;j<=n;j++){
            List<WebElement> productNameOnPage = new ProductPage(driver).getProductName();
            String productNameOnPageText = productNameOnPage.get(j).getText();
            List<WebElement> productNameOnCart = new CartPage(driver).getProductName();
            String productNameOnCartText = productNameOnCart.get(j).getText();
            softAssert.assertEquals(productNameOnPageText,productNameOnCartText,"Error: ProductName on the cart is incorrect");

        }
    }

    //------------------------CHECKOUT TEST---------------------------------------------------------

    @Test
    public void verifyYourInformationLabel(){
        String yourInfoLabel = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton().getYourInformationLabel();
        softAssert.assertEquals(yourInfoLabel.trim(),"CHECKOUT: YOUR INFORMATION","Error : your Information Label is incorrect");
    }

    @Test
    public void validateSuccessfulCheckOut(){
        String overviewLabel = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton()
                .enterYourInformation("Sandamini","Perera","10250").clickContinueButton().getOverviewLabel();
        softAssert.assertEquals(overviewLabel.trim(),"CHECKOUT: OVERVIEW","Error : overview Label is incorrect");
        softAssert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-two.html","Error : navigate page is incorrect");
    }

    @Test
     public void blankFirstName(){
        String errorText = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton().enterLastName("Perera")
                .enterZipCode("10250").clickContinueButton().getErrorMessage();
        softAssert.assertEquals(errorText.trim(),"Error: First Name is required","Error : Error message is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void blankLastName(){
        String errorText = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton().enterFirstName("Sandamini")
                .enterZipCode("10250").clickContinueButton().getErrorMessage();
        softAssert.assertEquals(errorText.trim(),"Error: Last Name is required","Error : Error message is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void blankZipCode(){
        String errorText = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton().enterFirstName("Sandamini")
                .enterLastName("Perera").clickContinueButton().getErrorMessage();
        softAssert.assertEquals(errorText.trim(),"Error: Postal Code is required","Error : Error message is incorrect");
        softAssert.assertAll();
    }

    @Test
    public void validateTotal(){
        int size = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .noOfAddToCartButtons();
        int n = ThreadLocalRandom.current().nextInt(0,size-1);
        for(int i=0;i<=n;i++) {
            new ProductPage(driver).addAnItemToCart(i);
        }
        List<WebElement> itemPrice = new ProductPage(driver).clickCart().clickCheckoutButton()
                .enterYourInformation("Sandamini","Perera","10250")
                .clickContinueButton().itemPrice();
        double itemTotal=0.0;

        for(int j=0;j<=n;j++){
            String itemPriceString=itemPrice.get(j).getText();
            double doublePrice = Double.parseDouble(itemPriceString.trim().substring(1));
            itemTotal=itemTotal+doublePrice;
        }

        String subTotalLabel = new CheckOutPage(driver).getSubTotalPrice();
        double subTotalPrice = Double.parseDouble(subTotalLabel.trim().substring(13));
        String taxLabel = new CheckOutPage(driver).getTax();
        double tax= Double.parseDouble(taxLabel.trim().substring(6));
        String totalLabel = new CheckOutPage(driver).getTotal();
        double total = Double.parseDouble(totalLabel.trim().substring(8));
        double calculatedTotal = subTotalPrice + tax;
        double roundOffTotal = Math.round(calculatedTotal*100.0)/100.0;
        softAssert.assertEquals(itemTotal,subTotalPrice,"Error : Item Total is incorrect");
        softAssert.assertEquals(roundOffTotal,total,"Error : Total is incorrect");
    }

    @Test
    public void validateCompletePlcaeOrder(){
        String completeOrderLabel = new LoginPage(driver).successfulLogin("standard_user","secret_sauce")
                .addAnItemToCart(0).clickCart().clickCheckoutButton()
                .enterYourInformation("Sandamini","Perera","10250")
                .clickContinueButton().clickFinish().getCompleteLabel();
        softAssert.assertEquals(completeOrderLabel,"THANK YOU FOR YOUR ORDER","Error : label is incorrect");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}

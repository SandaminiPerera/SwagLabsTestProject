package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Configurations {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void navigateToURL(String url){
        driver.get(url);
    }
}

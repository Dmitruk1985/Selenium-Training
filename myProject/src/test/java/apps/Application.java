package apps;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.CartPage;
import pages.CustomerPage;
import pages.ItemPage;

public class Application {
    private WebDriver driver;
    CustomerPage customer;
    CartPage cart;
    ItemPage item;


    public void start() {
        driver = new FirefoxDriver();
        customer=new CustomerPage(driver);
        cart = new CartPage(driver);
        item = new ItemPage(driver);
    }

    public void stop() {
        driver.quit();
        driver = null;
    }
}

import apps.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.CustomerPage;
import pages.ItemPage;

public class CartObjectTest {
    private WebDriver driver;
    CustomerPage customer;
    CartPage cart;
    ItemPage item;
    
    @BeforeEach
    public void openBrowser() {
        driver = new FirefoxDriver();
        customer = new CustomerPage(driver);
        cart = new CartPage(driver);
        item = new ItemPage(driver);
    }

    @Test
    public void cartObject() {
        customer.open();
        customer.addSomeItemsToCart(3, item);
        cart.deleteAllItemsFromCart();
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

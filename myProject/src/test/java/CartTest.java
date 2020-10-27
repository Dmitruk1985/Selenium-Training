import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void cart() {
        driver.get("http://localhost/litecart/en/");
        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("li[class='product column shadow hover-light']")).click();
            if (isElementPresent(driver, By.name("options[Size]"))) {
                Select select = new Select(driver.findElement(By.name("options[Size]")));
                select.selectByValue("Small");
            }
            driver.findElement(By.name("add_cart_product")).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("span.quantity"), Integer.toString(i)));
            driver.navigate().back();
        }
        driver.findElement(By.linkText("Checkout »")).click();
        int size = driver.findElements(By.xpath("//table[@class='dataTable rounded-corners']//td[contains(@style, 'text-align: center;')]")).size();
        for (int i = size; i > 0; i--) {
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath("//table[@class='dataTable rounded-corners']//td[contains(@style, 'text-align: center;')]"), i));
        }
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @AfterEach
    public void closeBrowser() {
          driver.quit();
          driver=null;
    }
}

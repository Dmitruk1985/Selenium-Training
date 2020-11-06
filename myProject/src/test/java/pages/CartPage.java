package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends Page {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void deleteAllItemsFromCart() {
        String itemLocator = "//table[@class='dataTable rounded-corners']//td[contains(@style, 'text-align: center;')]";
        driver.findElement(By.linkText("Checkout »")).click();
        for (int i = driver.findElements(By.xpath(itemLocator)).size(); i > 0; i--) {
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(By.xpath(itemLocator), i));
        }
    }
}

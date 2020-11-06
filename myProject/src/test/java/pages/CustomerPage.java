package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CustomerPage extends Page {
    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    public CustomerPage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    public void addSomeItemsToCart(int items, ItemPage item) {
        for (int i = 1; i < (items + 1); i++) {
            driver.findElement(By.cssSelector("li[class*='product']")).click();
            item.addItemToCart();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("span.quantity"), Integer.toString(i)));
            driver.navigate().back();
        }
    }
}

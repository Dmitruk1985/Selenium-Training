package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ItemPage extends Page {
    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart(){
        if (isElementPresent(driver, By.name("options[Size]"))) {
            Select select = new Select(driver.findElement(By.name("options[Size]")));
            select.selectByValue("Small");
        }
        driver.findElement(By.name("add_cart_product")).click();
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }
}

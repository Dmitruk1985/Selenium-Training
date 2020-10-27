import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AddNewItemTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
     public void loginAdmin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.xpath("//span[text()='Catalog']/parent::a"))).click();
        wait.until(presenceOfElementLocated(By.xpath("//a[text()=' Add New Product']"))).click();
        //1. General
        wait.until(presenceOfElementLocated(By.xpath("//label[text()=' Enabled']/input"))).click();
        String name = "New Item " + (int) (Math.random() * 1000);
        driver.findElement(By.name("name[en]")).sendKeys(name);
        driver.findElement(By.name("code")).sendKeys("code");
        driver.findElement(By.cssSelector("input[data-name='Rubber Ducks']")).click();
        driver.findElement(By.cssSelector("input[value='1-2']")).click();
        driver.findElement(By.name("quantity")).sendKeys("1"+Keys.DELETE);
        File file = new File("");
        driver.findElement(By.name("new_images[]")).sendKeys(file.getAbsolutePath() + "\\images\\1.jpg");
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("ddMMyyyy");
        driver.findElement(By.name("date_valid_from")).sendKeys(formatForDateNow.format(dateNow));
        driver.findElement(By.name("date_valid_to")).sendKeys(formatForDateNow.format(dateNow));
        //2. Information
        driver.findElement(By.xpath("//a[text()='Information']")).click();
        wait.until(presenceOfElementLocated(By.name("manufacturer_id")));
        Select selectManufacturer = new Select(driver.findElement(By.name("manufacturer_id")));
        selectManufacturer.selectByValue("1");
        driver.findElement(By.name("keywords")).sendKeys("keywords");
        driver.findElement(By.name("short_description[en]")).sendKeys("Short Description");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Description");
        driver.findElement(By.name("head_title[en]")).sendKeys("Head Title");
        driver.findElement(By.name("meta_description[en]")).sendKeys("Meta Description");
        //3. Prices
        driver.findElement(By.xpath("//a[text()='Prices']")).click();
        wait.until(presenceOfElementLocated(By.name("purchase_price"))).sendKeys("1"+Keys.DELETE);
        Select selectCurrency = new Select(driver.findElement(By.name("purchase_price_currency_code")));
        selectCurrency.selectByVisibleText("US Dollars");
        driver.findElement(By.name("prices[USD]")).sendKeys("2"+Keys.DELETE);
        driver.findElement(By.name("prices[EUR]")).sendKeys("1"+Keys.DELETE);
        driver.findElement(By.name("save")).click();
        //После сохранения товара нужно убедиться, что он появился в каталоге (в админке)
        wait.until(presenceOfElementLocated(By.linkText(name)));
    }

    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver=null;
    }
}

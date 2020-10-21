import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ZonesSortingTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
     public void listsSorting() throws InterruptedException {
        //Вход на страницу админа
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("tr.row td:nth-of-type(3) a")));
        ArrayList<String> zones = new ArrayList<String>();
        ArrayList<String> zonesSorted = new ArrayList<String>();
        int size = driver.findElements(By.cssSelector("tr.row td:nth-of-type(3) a")).size();
        for (int i = 0; i < size; i++) {
            wait.until(presenceOfElementLocated(By.cssSelector("tr.row td:nth-of-type(3) a")));
            driver.findElements(By.cssSelector("tr.row td:nth-of-type(3) a")).get(i).click();
            wait.until(presenceOfElementLocated(By.cssSelector("select[name*='zone_code']")));
            int size2 = driver.findElements(By.cssSelector("select[name*='zone_code']")).size();
            for (int j = 0; j < size2; j++) {
                Select select = new Select(driver.findElements(By.cssSelector("select[name*='zone_code']")).get(j));
                zones.add(select.getFirstSelectedOption().getText());
                zonesSorted.add(select.getFirstSelectedOption().getText());
            }
            Collections.sort(zonesSorted);
            for (int j = 0; j < size2; j++) {
                Assertions.assertEquals(zones.get(j),zonesSorted.get(j));
            }
            zones.clear();
            zonesSorted.clear();
            driver.navigate().back();
        }
    }

    @AfterEach
    public void closeBrowser(){
      driver.quit();
      driver=null;
    }
}

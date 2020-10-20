import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class ListsSortingTest {
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
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("tr.row td:nth-of-type(5)")));
        ArrayList<String> countries = new ArrayList<String>();
        ArrayList<String> countriesSorted = new ArrayList<String>();
        ArrayList<String> zones = new ArrayList<String>();
        ArrayList<String> zonesSorted = new ArrayList<String>();
        int size = driver.findElements(By.cssSelector("tr.row td:nth-of-type(5)")).size();
        //а) проверить, что страны расположены в алфавитном порядке
        for (int i = 0; i < size; i++) {
            countries.add(driver.findElements(By.cssSelector("tr.row td:nth-of-type(5)")).get(i).getText());
            countriesSorted.add(driver.findElements(By.cssSelector("tr.row td:nth-of-type(5)")).get(i).getText());
        }
        Collections.sort(countriesSorted);
        for (int i = 0; i < size; i++) {
           Assertions.assertEquals(countries.get(i),countriesSorted.get(i));
        }

        //б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
        for (int i = 0; i < size; i++) {
            int zonesQuantity = Integer.parseInt(driver.findElements(By.cssSelector("tr.row td:nth-of-type(6)")).get(i).getText());
            if(zonesQuantity>0){
                driver.findElements(By.cssSelector("tr.row td:nth-of-type(5) a")).get(i).click();
                int size2 = driver.findElements(By.cssSelector("table.dataTable td:nth-of-type(3)")).size() - 1;
                for (int j = 0; j < size2; j++) {
                    zones.add(driver.findElements(By.cssSelector("table.dataTable td:nth-of-type(3)")).get(j).getText());
                    zonesSorted.add(driver.findElements(By.cssSelector("table.dataTable td:nth-of-type(3)")).get(j).getText());
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

    }


    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver=null;
    }
}

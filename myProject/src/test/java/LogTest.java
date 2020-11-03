import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class LogTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void log() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.cssSelector("table.dataTable img+a")));
        int size = driver.findElements(By.cssSelector("table.dataTable img+a")).size();
        for (int i = 0; i < size; i++) {
            driver.findElements(By.cssSelector("table.dataTable img+a")).get(i).click();
            wait.until(presenceOfElementLocated(By.name("name[en]")));
            for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
                Assertions.assertEquals("", l);
            }
            driver.navigate().back();
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class NewWindowTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void newWindow() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector("a[title='Edit']")).click();
        String linkLocator = "//i[@class='fa fa-external-link']/parent::a";
        wait.until(presenceOfElementLocated(By.xpath(linkLocator)));
        String mainWindow = driver.getWindowHandle();
        int size = driver.findElements(By.xpath(linkLocator)).size();
        for (int i = 0; i < size; i++) {
            Set<String> oldWindows = driver.getWindowHandles();
            driver.findElements(By.xpath(linkLocator)).get(i).click();

            String newWindow = wait.until((WebDriver d) -> {
                Set<String> newWindows = d.getWindowHandles();
                String window = "";
                Iterator<String> it = newWindows.iterator();
                while (it.hasNext()) {
                    String currentWindow = it.next();
                    if (!oldWindows.contains(currentWindow)) {
                        window = currentWindow;
                        break;
                    }
                }
                return window;
            });
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

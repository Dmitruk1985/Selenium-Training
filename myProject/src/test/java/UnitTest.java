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

public class UnitTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
        // driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @Test
    public void newWindow() throws InterruptedException {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector("a[title='Edit']")).click();
        wait.until(presenceOfElementLocated(By.xpath("//i[@class='fa fa-external-link']/parent::a")));
        String mainWindow = driver.getWindowHandle();
        System.out.println(mainWindow);
        int size = driver.findElements(By.xpath("//i[@class='fa fa-external-link']/parent::a")).size();

            Set<String> oldWindows = driver.getWindowHandles();
            driver.findElements(By.xpath("//i[@class='fa fa-external-link']/parent::a")).get(0).click();
            Thread.sleep(1000);

      /* String newWindow = wait.until((WebDriver d) -> {
    Set<String> newWindows = driver.getWindowHandles();
    String window = "";
    Iterator<String> it = newWindows.iterator();
    while (it.hasNext()) {
        String currentWindow=it.next();
        if (!oldWindows.contains(currentWindow)) {
            window = currentWindow;
            break;
        }
    }
            System.out.println("window "+window);
    return window;
});*/
        driver.switchTo().window(thereIsWindowOtherThan(oldWindows));
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    String thereIsWindowOtherThan(Set<String> oldWindows) {
        Set<String> newWindows = driver.getWindowHandles();
        String newWindow = "";
        Iterator<String> it = newWindows.iterator();
        while (it.hasNext()) {
            String currentWindow=it.next();
            if (!oldWindows.contains(currentWindow)) {
                newWindow = currentWindow;
                break;
            }
        }
        return newWindow;
    }

    @AfterEach
    public void closeBrowser() {
        // driver.quit();
        //  driver=null;
    }
}

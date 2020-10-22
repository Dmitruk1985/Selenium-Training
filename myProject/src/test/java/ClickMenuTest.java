import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClickMenuTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void clickMenu() {
        //Логин в аккаунт админа
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Локаторы
        String outerLink = "li#app->a";
        String innerLink = "ul.docs a";
        String headline = "td#content h1";
        wait.until(presenceOfElementLocated(By.cssSelector(outerLink)));

        //Клик по вшеншним элементам списка
        int size = driver.findElements(By.cssSelector(outerLink)).size();
        for (int i = 0; i < size; i++) {
            driver.findElements(By.cssSelector(outerLink)).get(i).click();
            System.out.println("    Внешний пункт " + (i + 1));
            //Проверка наличия заголовка
            // wait.until(presenceOfElementLocated(By.cssSelector(headline)));

            //Клик по вложенным пунктам (если они есть), начиная со второго (первый включается автоматически)
            int size2 = driver.findElements(By.cssSelector(innerLink)).size();
            for (int j = 1; j < size2; j++) {
                driver.findElements(By.cssSelector(innerLink)).get(j).click();
                //Проверка наличия заголовка
                wait.until(presenceOfElementLocated(By.cssSelector(headline)));
                System.out.println("Вложенный пункт " + (i + 1) + "." + (j + 1));
            }
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }

}

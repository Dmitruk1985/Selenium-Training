import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RegisterNewUserTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
    public void registerNewUser() {
        //1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями,
        // в том числе при предыдущих запусках того же самого сценария),
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        wait.until(presenceOfElementLocated(By.name("firstname"))).sendKeys("firstname");
        driver.findElement(By.name("lastname")).sendKeys("lastname");
        driver.findElement(By.name("address1")).sendKeys("address1");
        driver.findElement(By.name("postcode")).sendKeys("11111");
        driver.findElement(By.name("city")).sendKeys("city");
        driver.findElement(By.cssSelector("span.select2-selection__arrow")).click();
        wait.until(presenceOfElementLocated(By.xpath("//li[text()='United States']"))).click();
        String email = "test" + (int) (Math.random() * 10000) + "@test.com";
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys(Keys.HOME + "+375291111111");
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.name("confirmed_password")).sendKeys("password");
        driver.findElement(By.name("create_account")).click();
        //2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
        wait.until(presenceOfElementLocated(By.linkText("Logout"))).click();
        //3) повторный вход в только что созданную учётную запись,
        wait.until(presenceOfElementLocated(By.name("email"))).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("password");
        driver.findElement(By.name("login")).click();
        //4) и ещё раз выход.
        wait.until(presenceOfElementLocated(By.linkText("Logout"))).click();
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

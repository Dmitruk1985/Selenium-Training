import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckStickersTest {
    private WebDriver driver;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkStickers() {
        driver.get("http://localhost/litecart/en/");
        int size = driver.findElements(By.cssSelector("li[class*='product']")).size();
        for (int j = 0; j < size; j++) {
            int stickers = driver.findElements(By.cssSelector("li[class*='product']")).get(j).findElements(By.cssSelector("div[class*=sticker]")).size();
            Assertions.assertEquals(1, stickers);
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

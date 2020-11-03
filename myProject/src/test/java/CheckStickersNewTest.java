import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckStickersNewTest {
    private WebDriver driver;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkStickers() {
        driver.get("http://localhost/litecart/en/");

        int size = driver.findElements(By.cssSelector("div#box-most-popular li[class*='product']")).size();
        System.out.println(size);
        for (int i = 1; i <= size; i++) {
            int size2 = driver.findElements(By.cssSelector("li[class*='product']:nth-of-type(" + i + ")")).size();
            System.out.println(size2);
            for (int j = 1; j <= size2; j++) {
                int stickers = driver.findElements(By.cssSelector("li[class*='product']:nth-of-type(" + j + ") div[class*=sticker]")).size();
                Assertions.assertEquals(1, stickers);
            }
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

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
        String locator = "";
        for (int i = 1; i <= 3; i++) {
            switch (i) {
                case 1:
                    locator = "box-most-popular";
                    break;
                case 2:
                    locator = "box-campaigns";
                    break;
                case 3:
                    locator = "box-latest-products";
                    break;
            }
            int size = driver.findElements(By.cssSelector("div#" + locator + " li[class='product column shadow hover-light']")).size();
            for (int j = 1; j <= size; j++) {
                int stickers = driver.findElements(By.cssSelector("div#" + locator + " li[class='product column shadow hover-light']:nth-of-type(" + j + ") div[class*=sticker]")).size();
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

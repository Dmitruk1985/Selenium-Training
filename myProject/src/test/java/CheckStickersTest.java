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
    public void openBrowser(){
        driver = new ChromeDriver();
    }

    @Test
    public void checkStickers() {
       driver.get("http://localhost/litecart/en/");


       int size = driver.findElements(By.cssSelector("li[class='product column shadow hover-light']")).size();
        for (int i = 0; i < size; i++) {
            driver.findElements(By.cssSelector("li[class='product column shadow hover-light'] div[class*=sticker]")).get(i);
        }
     /*  //Определение количества стикеров
       int stickers = driver.findElements(By.cssSelector("li[class='product column shadow hover-light'] div[class*=sticker]")).size();
       //Проверка равенства этих количеств
        Assertions.assertEquals(cards,stickers);
        //Проверка отсутствия карточек, у которых есть два или более стикера
       int doubleSticker = driver.findElements(By.cssSelector("div[class*=sticker]:nth-of-type(2)")).size();
        Assertions.assertEquals(0,doubleSticker);*/
    }

    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver=null;
    }
}

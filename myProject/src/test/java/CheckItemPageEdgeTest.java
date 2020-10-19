import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class CheckItemPageEdgeTest {
    private WebDriver driver;

    @BeforeEach
    public void openBrowser() {
        System.setProperty("webdriver.edge.driver", "C://drivers/msedgedriver.exe");
        driver = new EdgeDriver();
    }

    @Test
    public void checkItemPage() {
        driver.get("http://localhost/litecart/en/");
        //Элементы на главной странице
        String nameListing = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::div[@class='name']")).getText();
        String regularPriceListing = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::s[@class='regular-price']")).getText();
        String campaignPriceListing = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::strong[@class='campaign-price']")).getText();
        String regularPriceColor = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::s[@class='regular-price']")).getCssValue("color").substring(5).replaceAll("\\s", "");
        String[] regularPriceColors = regularPriceColor.split(",");
        String regularPriceDecoration = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::s[@class='regular-price']")).getCssValue("text-decoration");
        String campaignPriceColorListing = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::strong[@class='campaign-price']")).getCssValue("color").substring(5).replaceAll("\\s", "");
        String[] campaignPriceColors = campaignPriceColorListing.split(",");
        int campaignPriceWeight = Integer.parseInt(driver.findElement(By.xpath("//h3[text()='Campaigns']/following::strong[@class='campaign-price']")).getCssValue("font-weight"));
        String regularPriceFontSizePx = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::s[@class='regular-price']")).getCssValue("font-size");
        double regularPriceFontSize = Double.parseDouble(regularPriceFontSizePx.substring(0, regularPriceFontSizePx.length() - 2));
        String campaignPriceFontSizePx = driver.findElement(By.xpath("//h3[text()='Campaigns']/following::strong[@class='campaign-price']")).getCssValue("font-size");
        double campaignPriceFontSize = Double.parseDouble(campaignPriceFontSizePx.substring(0, campaignPriceFontSizePx.length() - 2));
        //Элементы на странице товара
        driver.findElement(By.xpath("//h3[text()='Campaigns']/following::a")).click();
        String namePage = driver.findElement(By.cssSelector("h1.title")).getText();
        String regularPricePage = driver.findElement(By.cssSelector("s.regular-price")).getText();
        String campaignPricePage = driver.findElement(By.cssSelector("strong.campaign-price")).getText();

        //а) на главной странице и на странице товара совпадает текст названия товара
        Assertions.assertEquals(nameListing, namePage);
        //б) на главной странице и на странице товара совпадают цены (обычная и акционная)
        Assertions.assertEquals(regularPriceListing, regularPricePage);
        Assertions.assertEquals(campaignPriceListing, campaignPricePage);
        //в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        //г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
        //(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
        //д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)

        //1. Проверка на главной странице
        //в.1
        Assertions.assertEquals(regularPriceColors[0], regularPriceColors[1]);
        Assertions.assertEquals(regularPriceColors[1], regularPriceColors[2]);
        Assertions.assertTrue(regularPriceDecoration.contains("line-through"));
        //г.1
        Assertions.assertEquals("0", campaignPriceColors[1]);
        Assertions.assertEquals("0", campaignPriceColors[2]);
        Assertions.assertTrue(campaignPriceWeight>=700);
        //д.1
        Assertions.assertTrue(campaignPriceFontSize > regularPriceFontSize);

        //2. Проверка на странице каждого товара
        driver.navigate().back();
        //Цикл для клика на всех товарах, имеющих обычную и акционную цену
        int size = driver.findElements(By.cssSelector("s.regular-price")).size();
        for (int i = 0; i < size; i++) {
            driver.findElements(By.cssSelector("s.regular-price")).get(i).click();
            //Элементы на странице товара
            String campaignPriceColorItem = driver.findElement(By.cssSelector("s.regular-price")).getCssValue("color").substring(5).replaceAll("\\s", "");
            String[] regularPriceColorsItem = campaignPriceColorItem.split(",");
            String regularPriceDecorationItem = driver.findElement(By.cssSelector("s.regular-price")).getCssValue("text-decoration");
            String campaignPriceColorListingItem = driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color").substring(5).replaceAll("\\s", "");
            String[] campaignPriceColorsItem = campaignPriceColorListingItem.split(",");
            int campaignPriceWeightItem = Integer.parseInt(driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-weight"));
            String regularPriceFontSizePxItem = driver.findElement(By.cssSelector("s.regular-price")).getCssValue("font-size");
            double regularPriceFontSizeItem = Double.parseDouble(regularPriceFontSizePxItem.substring(0, regularPriceFontSizePxItem.length() - 2));
            String campaignPriceFontSizePxItem = driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("font-size");
            double campaignPriceFontSizeItem = Double.parseDouble(campaignPriceFontSizePxItem.substring(0, campaignPriceFontSizePxItem.length() - 2));
            //в.2
            Assertions.assertEquals(regularPriceColorsItem[0], regularPriceColorsItem[1]);
            Assertions.assertEquals(regularPriceColorsItem[1], regularPriceColorsItem[2]);
            Assertions.assertTrue(regularPriceDecorationItem.contains("line-through"));
            //г.2
            Assertions.assertEquals("0", campaignPriceColorsItem[1]);
            Assertions.assertEquals("0", campaignPriceColorsItem[2]);
            Assertions.assertTrue(campaignPriceWeightItem>=700);
            //д.2
            Assertions.assertTrue(campaignPriceFontSizeItem > regularPriceFontSizeItem);
            driver.navigate().back();
        }
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    public static void main(String[] arg) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        driver.findElement(By.name("q")).sendKeys("Selenium");
        driver.findElement(By.xpath("//div[@class='FPdoLc tfB0Bf']//input[@name='btnK']")).click();
        driver.quit();
    }
}

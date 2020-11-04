import net.lightbody.bmp.core.har.Har;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProxyFiddlerTest {
    private WebDriver driver;
    private WebDriverWait wait;
    public Proxy proxy;

    @BeforeEach
    public void openBrowser(){
        proxy = new Proxy();
        proxy.setHttpProxy("localhost:8866");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("proxy", proxy);
        driver = new ChromeDriver(caps);
       // driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10/*seconds*/);
    }

    @Test
     public void loginAdmin() {
        driver.get("http://localhost/litecart/");
        driver.findElement(By.cssSelector("li[class*='product'] a")).click();
     //    System.out.println(proxy.getNoProxy());

       // Har har = proxy.getHar();
       // har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() +" : "+l.getRequest().getUrl()));
    }

    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver=null;
    }
}

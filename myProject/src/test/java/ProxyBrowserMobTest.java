import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProxyBrowserMobTest {
    private WebDriver driver;
    private WebDriverWait wait;
    public BrowserMobProxyServer proxy;

    @BeforeEach
    public void openBrowser() {
        proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.PROXY, seleniumProxy);
        driver = new EventFiringWebDriver(new ChromeDriver(caps));
    }

    @Test
    public void proxy() {
        proxy.newHar();
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        Har har = proxy.getHar();
        har.getLog().getEntries().forEach(l -> System.out.println(l.getResponse().getStatus() +" : "+l.getRequest().getUrl()));
    }

    @AfterEach
    public void closeBrowser() {
        driver.quit();
        driver = null;
    }
}

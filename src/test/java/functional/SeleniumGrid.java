import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SeleniumGrid {

    WebDriver driver;
    String baseURL, nodeURL;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        baseURL = "https://boutiquerugs.com/";
        nodeURL = "http://192.168.86.33:4444/wd/hub";
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.WINDOWS);

        driver = new RemoteWebDriver(new URL(nodeURL), capability);
    }

    @Test
    public void sampleTest() {
        driver.get(baseURL);

    }


    @AfterTest
    public void afterTest() {
        driver.quit();
    }


}

/**
    HUB -- java -jar selenium-server-standalone-3.141.59.jar -role hub
    HUB IP -- 192.168.86.33


    NODE -- java -Dwebdriver.chrome.driver="C:\Users\Boutique Rugs\Desktop\chromedriver.exe" -jar selenium-server-standalone-3.141.59.jar -role webdriver -hub http://192.168.86.249:4444/grid/register -port 5555
    NODE IP -- 192.168.86.32

    Selenium hub, node and client' jar versions need to be the same !!

        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-remote-driver</artifactId>
            <version>3.141.59</version>
        </dependency>

 */
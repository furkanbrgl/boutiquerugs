package base;

import core.ScreenShot;
import core.environment.Environment;
import core.environment.EnvironmentUtil;
import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import core.report.model.ReportHeader;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import util.DateUtil;
import util.ReportStepType;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    final Logger LOGGER = Logger.getLogger(BaseTest.class);

    public DesiredCapabilities capability = null;
    protected static WebDriver webDriver;
    public static ReportBuilder testReportBuilder = new ReportBuilderWord();

    private String nodeTag = EnvironmentUtil.getInstance().getNodeTagName();

    public String testID = EnvironmentUtil.getInstance().getTestId();
    public String getReportFilePath = EnvironmentUtil.getInstance().getReportFilePath();
    public String getReportFilePathWithTestId = getReportFilePath + File.separator + testID + File.separator + testID + ".docx";

    private String url = EnvironmentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnvironmentUtil.getInstance().getChromeDriver();
    private String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();
    private String testName = EnvironmentUtil.getInstance().getTestName();
    private String userEmail = EnvironmentUtil.getInstance().getBrEmail();
    private String hubIpAddress =EnvironmentUtil.getInstance().getHubIpAddress();


    @BeforeTest
    public void setUp() throws Exception {

            File directory = new File(getReportFilePath + File.separator + testID);
            if (! directory.exists()){
                directory.mkdir();
            }

            //mapping will be later
            ReportHeader reportHeader = new ReportHeader(testID,
                    testName, userEmail,
                    new Date(), new Date() ,
                    Environment.PROD,
                    new HashMap<String, String>());
            testReportBuilder.addHeader(reportHeader);

            LOGGER.info("test is initializing... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));

            System.setProperty(driverType, driverPath);
            LOGGER.info("System Properties was set... ");

            capability = DesiredCapabilities.chrome();
            LOGGER.info("Chrome Driver was set as a web driver ");

            //TODO: will be fixed
            capability.setBrowserName("chrome");
            capability.setPlatform(Platform.WINDOWS);
            capability.setCapability("nodeTag", nodeTag);

            // Solution for Timed out receiving message from renderer: 600.000
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("enable-automation");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-browser-side-navigation");
            options.addArguments("--disable-gpu");

            capability.setCapability(ChromeOptions.CAPABILITY, options);

            webDriver = new RemoteWebDriver(new URL(hubIpAddress), capability);
            webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(60, TimeUnit.SECONDS);
            webDriver.manage().window().maximize();

            // Solution for Timed out receiving message from renderer: 600.000
            System.setProperty("webdriver.chrome.silentOutput", "true");

            LOGGER.info("tolerable waiting time is 20 SECOND and page load timeout is 60 SECOND for Web Driver !");

            webDriver.get(url);

            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,testID,
                    "Test Has Been Started",
                    "Boutique Rugs Quality Assurance Test",
                    ReportStepType.INFO,
                    testReportBuilder);

    }

    @AfterTest
    public void afterSuite() {
        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }
}

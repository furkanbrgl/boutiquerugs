package core;

import core.environment.Environment;
import core.environment.EnvironmentUtil;
import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import core.report.model.ReportHeader;
import core.report.model.ReportStep;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import util.DateUtil;
import util.ReportStepType;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SeleniumRemoteBaseTest {

    final Logger LOGGER = Logger.getLogger(SeleniumRemoteBaseTest.class);

    public DesiredCapabilities capability = null;
    public WebDriver webDriver = null;
    public ReportBuilder reportBuilder = new ReportBuilderWord();
    public String testID = EnvironmentUtil.getInstance().getTestId();
    public String getReportFilePathWithTestId = EnvironmentUtil.getInstance().getReportFilePath();


    private String url = EnvironmentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnvironmentUtil.getInstance().getChromeDriver();
    private String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();
    private String testName = EnvironmentUtil.getInstance().getTestName();
    private String userEmail = EnvironmentUtil.getInstance().getBrEmail();
    private String nodeTag = EnvironmentUtil.getInstance().getNodeTagName();
    private String nodeIp = EnvironmentUtil.getInstance().getNodeIp();


    @Before
    public final void setUp() throws Exception {

        try {

            File directory = new File(getReportFilePathWithTestId + File.separator + testID);
            if (! directory.exists()){
                directory.mkdir();
            }
            this.getReportFilePathWithTestId = directory.getPath() + File.separator + testID + ".docx";


            //mapping will be later
            ReportHeader reportHeader = new ReportHeader(testID,
                    testName, userEmail,
                    new Date(), new Date() ,
                    Environment.PROD,
                    new HashMap<String, String>());
            reportBuilder.addHeader(reportHeader);

            LOGGER.info("test is initializing... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));

            System.setProperty(driverType, driverPath);
            LOGGER.info("System Properties was set... ");

            capability = DesiredCapabilities.chrome();
            LOGGER.info("Chrome Driver was set as a web driver ");

            //TODO: will be fixed
            capability.setBrowserName("chrome");
            capability.setPlatform(Platform.WINDOWS);
            capability.setCapability("nodeTag", nodeTag);

            webDriver = new RemoteWebDriver(new URL("http://192.168.86.33:4444/wd/hub"), capability);
            webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(60, TimeUnit.SECONDS);
            LOGGER.info("tolerable waiting time is 20 SECOND for Web Driver !");
            LOGGER.info("tolerable page load timeout is 60 SECOND for Web Driver !");

            webDriver.get(url);

            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,testID,
                    "Test Has Been Started",
                    "Boutique Rugs Quality Assurance Test",
                    ReportStepType.INFO,
                    reportBuilder);

        } catch (Exception e) {
            LOGGER.error("Page: " + url + " did not load within 60 seconds!");
            LOGGER.error("Environmental test problem" + e.getMessage());
            //webdriver has not been alive so we cant take a screenshot.
            ReportStep reportStep = new ReportStep(testID,
                    "Environmental Test Problem",
                    "Page: " + url + " did not load within 60 seconds! -- " + e.getMessage() ,
                    "ActiveURL",
                    "screenshots do not exist",
                    new Date(),
                    ReportStepType.ERROR);
            reportBuilder.addStep(reportStep);

            throw e;
        } finally {
            LOGGER.info("Base test is finalizing " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
            try {
                reportBuilder.buildReport(this.testID, getReportFilePathWithTestId );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public final void tearDown() throws Exception {

        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }

}


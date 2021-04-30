package core;

import core.environment.Environment;
import core.environment.EnvironmentUtil;
import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import core.report.model.ReportHeader;
import core.test.TestResult;
import core.test.TestStatus;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.DateUtil;
import util.ReportStepType;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    final Logger LOGGER = Logger.getLogger(BaseTest.class);

    public WebDriver webDriver = null;
    public String testCustomResult;
    public ReportBuilder reportBuilder = new ReportBuilderWord();
    public String testID = EnvironmentUtil.getInstance().getTestId();
    private String reportFilePath = EnvironmentUtil.getInstance().getReportFilePath();

    private String url = EnvironmentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnvironmentUtil.getInstance().getChromeDriver();
    private String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();
    private String testName = EnvironmentUtil.getInstance().getTestName();
    private String userEmail = EnvironmentUtil.getInstance().getBrEmail();
    public String getReportFilePathWithTestId = reportFilePath + testID + ".docx";


    @Before
    public final void setUp() throws Exception {

        try {

            //mapping will be later
            ReportHeader reportHeader = new ReportHeader(testID,
                    testName, userEmail,
                    new Date(), new Date() ,
                    Environment.PROD,
                    new HashMap<String, String>());
            reportBuilder.addHeader(reportHeader);

            TestResult.setTestResult(TestStatus.SUCCESSFUL);
            LOGGER.info("test is initializing... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));

            System.setProperty(driverType, driverPath);
            LOGGER.info("System Properties was set... ");

            webDriver = new ChromeDriver();
            LOGGER.info("Chrome Driver was set as a web driver ");

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
            TestResult.setTestResult(TestStatus.FAIL);
            LOGGER.error("Environmental test problem" + e.getMessage());
            throw e;

        } finally {
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            LOGGER.info("BASE TEST FINALLY");
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            LOGGER.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        }

    }

    @After
    public final void tearDown() throws Exception {

        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }

}

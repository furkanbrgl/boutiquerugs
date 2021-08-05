package base;

import core.ScreenShot;
import core.environment.Environment;
import core.environment.EnvironmentUtil;
import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import core.report.model.ReportHeader;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import util.DateUtil;
import util.ReportStepType;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class BaseTestT {

    final Logger LOGGER = Logger.getLogger(BaseTestT.class);

    public WebDriver webDriver = null;
    public ReportBuilder reportBuilder = new ReportBuilderWord();
    public String testID = EnvironmentUtil.getInstance().getTestId();
    public String getReportFilePathWithTestId = EnvironmentUtil.getInstance().getReportFilePath();

    private String url = EnvironmentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnvironmentUtil.getInstance().getChromeDriver();
    private String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();
    private String testName = EnvironmentUtil.getInstance().getTestName();
    private String userEmail = EnvironmentUtil.getInstance().getBrEmail();


    @BeforeTest
    public void setUp() throws Exception {

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

            webDriver = new ChromeDriver();
            LOGGER.info("Chrome Driver was set as a web driver ");

            webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS).pageLoadTimeout(60, TimeUnit.SECONDS);
            webDriver.manage().window().maximize();

            LOGGER.info("tolerable waiting time is 20 SECOND for Web Driver !");
            LOGGER.info("tolerable page load timeout is 60 SECOND for Web Driver !");

            webDriver.get(url);

            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,testID,
                    "Test Has Been Started",
                    "Boutique Rugs Quality Assurance Test",
                    ReportStepType.INFO,
                    reportBuilder);

    }

    @AfterTest
    public void afterSuite() {
        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }
}

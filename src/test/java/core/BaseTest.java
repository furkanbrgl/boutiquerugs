package core;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.DateUtil;
import util.EnviromentUtil;
import util.TestResult;
import util.email.ContentBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    final Logger LOGGER = Logger.getLogger(BaseTest.class);

    public WebDriver webDriver = null;
    public List<File> listOfScreenShotFiles = null;
    public ContentBuilder contentBuilder = new ContentBuilder();

    private String url = EnviromentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnviromentUtil.getInstance().getChromeDriver();
    private String driverPath = EnviromentUtil.getInstance().getChromeDriverPath();

    @Before
    public final void setUp() throws Exception {

        contentBuilder.setUpInitialContentParameters();

        listOfScreenShotFiles = new ArrayList<>();
        TestResult.setTestResult(TestStatus.SUCCESSFUL);
        LOGGER.info("test is initializing... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));

        System.setProperty(driverType, driverPath);
        LOGGER.info("System Properties was set... ");

        webDriver = new ChromeDriver();
        LOGGER.info("Chrome Driver was set as a web driver ");

        webDriver.get(url);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        LOGGER.info("tolerable waiting time is 10 SECOND for Web Driver !");

    }

    @After
    public final void tearDown() throws Exception {

        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }

}

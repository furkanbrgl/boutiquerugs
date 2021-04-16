package core;

import core.email.ContentBuilder;
import core.environment.EnvironmentUtil;
import core.test.TestResult;
import core.test.TestStatus;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import util.DateUtil;

import javax.mail.MessagingException;
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
    public String testCustomResult;

    private String url = EnvironmentUtil.getInstance().getResourceBaseURL();
    private String driverType = EnvironmentUtil.getInstance().getChromeDriver();
    private String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();

    @Before
    public final void setUp() {

        try {

            contentBuilder.setUpInitialContentParameters();

            listOfScreenShotFiles = new ArrayList<>();
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

        } catch (Exception e) {
            LOGGER.error("Page: " + url + " did not load within 60 seconds!");
            this.contentBuilder.setUpResultParameters();
            try {
                EmailSender.sendEmail("Environmental test problem", contentBuilder.getHTMLContent(e.getMessage()));
            } catch (MessagingException messagingException) {
                messagingException.printStackTrace();
            }
            throw e;
        }

    }

    @After
    public final void tearDown() throws Exception {

        webDriver.quit();
        LOGGER.info("test is ending... " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
    }

}

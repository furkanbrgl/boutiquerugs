package scenarios;

import core.BaseTest;
import core.EmailSender;
import core.ScreenShot;
import core.SeleniumUtil;
import core.environment.EnvironmentUtil;
import core.test.TestResult;
import core.test.TestStatus;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DateUtil;

import java.util.Date;

public class LoginTest extends BaseTest {

    final Logger LOGGER = Logger.getLogger(LoginTest.class);

    @Test
    public void LoginTest() {

        String brEmail = EnvironmentUtil.getInstance().getBrEmail();
        String brPassword = EnvironmentUtil.getInstance().getBrPassword();
        String orderTagXPath = "/html/body/div[3]/div[1]/h1";
        String passwordValidationMessageXPath = "/html/body/div[3]/div[1]/div/div[1]/p/span";
        String submitButtonXPath = "/html/body/div[3]/div[1]/div/div/form/div[3]/input";

        try {

            LOGGER.info("Login test is starting");
            WebDriverWait wait = new WebDriverWait(webDriver, 20);

            JavascriptExecutor javascript = (JavascriptExecutor) this.webDriver;
            javascript.executeScript("document.getElementsByClassName(\"account-icon\")[0].click()");

            LOGGER.info("Wait for items to be set up " + System.currentTimeMillis());
            SeleniumUtil.sleep(5000);
            LOGGER.info("Waited for items to be set up " + System.currentTimeMillis());

            WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
            emailElement.sendKeys(brEmail);
            WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_pass")));
            passwordElement.sendKeys(brPassword);


            if (!SeleniumUtil.existsElementByXpath(submitButtonXPath, webDriver)) {
                SeleniumUtil.sleep(4000);
            }
            webDriver.findElement(By.xpath(submitButtonXPath)).click();

            if (SeleniumUtil.existsElementByXpath(orderTagXPath, webDriver)) {
                LOGGER.info("Login Successful");
                this.testCustomResult = "LOGIN SUCCESSFUL" + System.lineSeparator();
            } else if (SeleniumUtil.existsElementByXpath(passwordValidationMessageXPath, webDriver)) {
                String message = webDriver.findElement(By.xpath(passwordValidationMessageXPath)).getText();
                LOGGER.info(message);
                TestResult.setTestResult(TestStatus.FAIL);
                this.testCustomResult = message + System.lineSeparator();
            }
            else{
                LOGGER.info("username password might not be correct");
                TestResult.setTestResult(TestStatus.FAIL);
                this.testCustomResult = "USERNAME PASSWORD MIGHT NOT BE CORRECT" + System.lineSeparator();

            }

        } catch (Exception e) {

            LOGGER.error(e.getCause() + "-------------" + e.getMessage());
            TestResult.setTestResult(TestStatus.FAIL);
            this.testCustomResult = e.getMessage();
            LOGGER.error(e.getCause() + "-------------" + e.getMessage());

        } finally {
            LOGGER.info("Login test is finalizing " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
            try {
                this.contentBuilder.setUpResultParameters();
                listOfScreenShotFiles = ScreenShot.takeSnapShot(webDriver);
                EmailSender.sendEmail(TestResult.getTestResult(), contentBuilder.getHTMLContent(this.testCustomResult), listOfScreenShotFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

package scenarios;

import core.BaseTest;
import core.EmailSender;
import core.ScreenShot;
import core.TestStatus;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DateUtil;
import util.EnviromentUtil;
import util.TestResult;

import java.util.Date;

public class LoginTest extends BaseTest {

    final Logger LOGGER = Logger.getLogger(LoginTest.class);

    @Test
    public void LoginTest() {

        String brEmail = EnviromentUtil.getInstance().getBrEmail();
        String brPassword = EnviromentUtil.getInstance().getBrPassword();

        try {

            LOGGER.info("Login test is starting");
            WebDriverWait wait = new WebDriverWait(webDriver, 10);

            JavascriptExecutor javascript = (JavascriptExecutor) this.webDriver;
            javascript.executeScript("document.getElementsByClassName(\"account-icon\")[0].click()");

            WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
            emailElement.sendKeys(brEmail);
            WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_pass")));
            passwordElement.sendKeys(brPassword);


            WebElement submitElement = webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/form/div[3]/input"));
            if (submitElement.isDisplayed()) {
                submitElement.click();
            } else {
                wait.until(ExpectedConditions.visibilityOf(submitElement));
            }


        } catch (Exception e) {

            LOGGER.error(e.getCause() + "-------------" + e.getMessage());
            TestResult.setTestResult(TestStatus.FAIL);
            LOGGER.error(e.getCause() + "-------------" + e.getMessage());

        } finally {
            LOGGER.error("Login test is finalizing " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
            try {
                this.contentBuilder.setUpResultParameters();
                listOfScreenShotFiles = ScreenShot.takeSnapShot(webDriver);
                EmailSender.sendEmail(TestResult.getTestResult(), contentBuilder.getHTMLContent(), listOfScreenShotFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

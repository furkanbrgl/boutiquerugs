package scenarios;

import core.BaseTest;
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
import util.ReportStepType;

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

            if (!SeleniumUtil.existsElementByXpath(orderTagXPath, webDriver)) {
                String message = webDriver.findElement(By.xpath(passwordValidationMessageXPath)).getText();LOGGER.info(message);
                TestResult.setTestResult(TestStatus.FAIL);
                this.testCustomResult = message + System.lineSeparator();
            } else {
                LOGGER.info("Login Successful");
                this.testCustomResult = "LOGIN SUCCESSFUL" + System.lineSeparator();
            }
            LOGGER.info(testCustomResult);

        } catch (Exception e) {

            LOGGER.error(e.getCause() + "------------- LOGIN TEST TRY-CATCH" + e.getMessage());
            TestResult.setTestResult(TestStatus.FAIL);
            this.testCustomResult = e.getMessage();
            LOGGER.info(testCustomResult);
            LOGGER.error(e.getCause() + "------------- LOGIN TEST TRY-CATCH" + e.getMessage());
            throw e;

        } finally {
            LOGGER.info("Login test is finalizing " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
            LOGGER.error("------------- LOGIN TEST FINALLY");
            try {
                ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                        "Test Has Been Completed",
                        "Boutique Rugs Quality Assurance Test",
                        ReportStepType.INFO,
                        reportBuilder);

                reportBuilder.buildReport(this.testID, getReportFilePathWithTestId );

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

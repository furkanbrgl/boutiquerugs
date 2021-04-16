package functional;

import core.EmailSender;
import core.ScreenShot;
import core.test.TestStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import core.environment.EnvironmentUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Dummy {

    private static List<File> listOfFlies;

    public static void main(String[] args) throws Exception {

        final Logger LOGGER = Logger.getLogger(Dummy.class);

        WebDriver webDriver = null;
        String url = EnvironmentUtil.getInstance().getResourceBaseURL();
        String driverType = EnvironmentUtil.getInstance().getChromeDriver();
        String driverPath = EnvironmentUtil.getInstance().getChromeDriverPath();

        String email = "brgl.furkan@gmail.com";
        String password = "Asd123!";

        try {
            System.setProperty(driverType, driverPath);

            webDriver = new ChromeDriver();
            webDriver.get(url);
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(webDriver,10);

            JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
            javascript.executeScript("document.getElementsByClassName(\"account-icon\")[0].click()");


            WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_email")));
            emailElement.sendKeys(email);

            WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login_pass")));
            passwordElement.sendKeys(password);


            WebElement submitElement = webDriver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/form/div[3]/input"));
            if (submitElement.isDisplayed()){
                submitElement.click();
            } else {
                wait.until(ExpectedConditions.visibilityOf(submitElement));
            }

        } catch (Exception e) {
            System.out.println(e);

        } finally {
            //screenshot
            List<File> file = ScreenShot.takeSnapShot(webDriver);
            //Send an email
            EmailSender.sendEmail(TestStatus.SUCCESSFUL.name(), "TEST BODY", file);

            webDriver.close();
        }

    }
}

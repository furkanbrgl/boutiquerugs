package pages;

import base.BasePage;
import core.ScreenShot;
import core.report.ReportBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.constants.LoginPageConstants;
import util.ReportStepType;

import java.io.IOException;

public class LoginPage extends BasePage implements LoginPageConstants {

    protected static final Logger LOGGER = Logger.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver, String testId, ReportBuilder reportBuilder) {
        super(driver, testId, reportBuilder);
    }

    public MainPage loginBoutiqueRugs(String brEmail, String brPassword) throws Exception {

        ScreenShot.takeSnapShotAndAddToReportStep(driver,this.testID,
                "Login PAGE !!!",
                "Boutique Rugs Quality Assurance Test",
                ReportStepType.INFO,
                reportBuilder);


        if(untilElementAppearBy(By.name(emailByName))){
            setObjectBy(By.name(emailByName), brEmail);
            if(untilElementAppearBy(By.name(passwordByName))) {
                setObjectBy(By.name(passwordByName), brPassword);
            }
        } else {
            throw new IOException("Username or Password could not set");
        }
        clickObjectByXpath(submitButtonXPath);

        waitForElement(5,By.xpath(loginValidationMessageXPath));

        if(isElementDisplayed(By.xpath(loginValidationMessageXPath))){
            ScreenShot.takeSnapShotAndAddToReportStep(driver,testID,
                    "Login is successful",
                    "Boutique Rugs Quality Assurance Test",
                    ReportStepType.INFO,
                    reportBuilder);

        } else {
            throw new IOException("Login could not accomplished successfully");
        }

        return new MainPage(driver, testID, reportBuilder);
    }


}

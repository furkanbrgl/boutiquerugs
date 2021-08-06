package pages;

import base.BasePage;
import core.report.ReportBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.constants.LoginPageConstants;

import java.io.IOException;

public class MainPage extends BasePage implements LoginPageConstants {

    protected static final Logger logger = Logger.getLogger(LoginPage.class);

    public MainPage(WebDriver driver, String TestId, ReportBuilder reportBuilder) {
        super(driver, TestId, reportBuilder);
    }

    public void logout() throws IOException {

    }
}

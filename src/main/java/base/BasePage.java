package base;

import core.report.ReportBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;

import java.io.IOException;

public class BasePage extends BasePageUtil{

    final Logger LOGGER = Logger.getLogger(BasePage.class);

    public BasePage(WebDriver driver, String testID, ReportBuilder reportBuilder) {
        super(driver, testID, reportBuilder);
    }

    public LoginPage callLoginPage() throws IOException {
        this.executeJS("document.getElementsByClassName(\"Icon Icon--account\")[0].dispatchEvent(new MouseEvent('click', {view: window, bubbles:true, cancelable: true}))", true);
        return new LoginPage(driver, testID, reportBuilder);
    }

}

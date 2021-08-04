package base;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;

import java.io.IOException;

public class BasePage extends BasePageUtil{

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage callLoginPage() throws IOException {
        this.executeJS("document.getElementsByClassName(\"Icon Icon--account\")[0].dispatchEvent(new MouseEvent('click', {view: window, bubbles:true, cancelable: true}))", true);
        return new LoginPage(driver);
    }

}

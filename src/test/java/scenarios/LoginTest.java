package scenarios;

import base.BaseTestT;
import core.environment.EnvironmentUtil;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MainPage;

@Listeners(util.Listener.class)
public class LoginTest extends BaseTestT {

    final Logger LOGGER = Logger.getLogger(LoginTest.class);

    @Test
    public void LoginTest() throws Exception {

        String brEmail = EnvironmentUtil.getInstance().getBrEmail();
        String brPassword = EnvironmentUtil.getInstance().getBrPassword();


        LOGGER.info("Login test is starting");
        new MainPage(webDriver, testID, testReportBuilder).callLoginPage().loginBoutiqueRugs(brEmail,brPassword);

    }
}

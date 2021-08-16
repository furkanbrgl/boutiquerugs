package scenarios;

import base.BaseTest;
import core.ScreenShot;
import core.environment.EnvironmentUtil;
import org.apache.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.constants.inputs.LoginPageInputs;
import util.ReportStepType;

import java.util.Date;
import java.util.HashMap;

@Listeners(util.Listener.class)
public class LoginTest extends BaseTest implements LoginPageInputs {

    final Logger LOGGER = Logger.getLogger(LoginTest.class);

    @Test
    public void LoginTest() throws Exception {

        String brEmail = EnvironmentUtil.getInstance().getBrEmail();
        String brPassword = EnvironmentUtil.getInstance().getBrPassword();

        LOGGER.info("Login test is starting");

        try {
            new MainPage(webDriver, testID, testReportBuilder).callLoginPage().loginBoutiqueRugs(brEmail,brPassword);
        } catch (Exception e) {
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "ERROR !! DETAIL :: " + e.getMessage()  ,
                    ReportStepType.ERROR,
                    testReportBuilder);
            throw e;
        }
        finally {

            testReportBuilder.gatherReport(testID).getReportHeader().setUsedParameters(this.setUsedTestParameters());
            testReportBuilder.gatherReport(testID).getReportHeader().setFinishTime(new Date());
        }
    }

    private HashMap<String, String> setUsedTestParameters() {
        HashMap<String, String> map = new HashMap<>();
        for (BaseOperation key : BaseOperation.values()) {
            map.put(key.toString(), System.getProperty(key.name()));
        }
        for (LoginOperation key : LoginOperation.values()) {
            map.put(key.toString(), System.getProperty(key.name()));
        }
        return map;
    }
}

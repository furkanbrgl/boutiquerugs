package scenarios;

import core.BaseTest;
import core.ScreenShot;
import core.environment.EnvironmentUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import pages.MainPage;
import util.DateUtil;
import util.ReportStepType;

import java.util.Date;

public class LoginTest extends BaseTest {

    final Logger LOGGER = Logger.getLogger(LoginTest.class);

    @Test
    public void LoginTest() throws Exception {

        String brEmail = EnvironmentUtil.getInstance().getBrEmail();
        String brPassword = EnvironmentUtil.getInstance().getBrPassword();

        try {

            LOGGER.info("Login test is starting");
            new MainPage(webDriver, testID, reportBuilder).callLoginPage().loginBoutiqueRugs(brEmail,brPassword);

        }
        catch (Exception e) {
            LOGGER.error(e.getCause() + "------------- LOGIN TEST TRY-CATCH" + e.getMessage());
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "Test Has Been Completed -- "  + e.getMessage(),
                    ReportStepType.ERROR,
                    reportBuilder);
            LOGGER.error(e.getCause() + "------------- LOGIN TEST TRY-CATCH" + e.getMessage());
            throw e;
        } finally {
            LOGGER.info("Login test is finalizing " + DateUtil.formatDateWithTime(new Date(System.currentTimeMillis())));
            try {
                reportBuilder.buildReport(this.testID, getReportFilePathWithTestId );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

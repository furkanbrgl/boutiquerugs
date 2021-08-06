package util;

import base.BaseTest;
import core.ScreenShot;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends BaseTest implements ITestListener {

    final Logger LOGGER = Logger.getLogger(BaseTest.class);

    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
    }

    public void onTestSuccess(ITestResult testResult) {

        try {
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "Test Has Been Completed",
                    ReportStepType.SUCCESS,
                    testReportBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        testReportBuilder.buildReport(this.testID, getReportFilePathWithTestId );

    }

    public void onTestFailure(ITestResult testResult) {

        try {
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "Test Has Been Completed",
                    ReportStepType.ERROR,
                    testReportBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        testReportBuilder.buildReport(this.testID, getReportFilePathWithTestId );

    }

    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub

    }
}
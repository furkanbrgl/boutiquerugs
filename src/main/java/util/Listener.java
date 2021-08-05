package util;

import base.BaseTestT;
import core.ScreenShot;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends BaseTestT implements ITestListener {

    final Logger LOGGER = Logger.getLogger(BaseTestT.class);

    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub

    }

    public void onTestSuccess(ITestResult testResult) {

        try {
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "Test Has Been Completed -- : RESULT : " + testResult.toString() + " ::::::::::: ",
                    ReportStepType.SUCCESS,
                    reportBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportBuilder.buildReport(this.testID, getReportFilePathWithTestId );

    }

    public void onTestFailure(ITestResult testResult) {

        try {
            ScreenShot.takeSnapShotAndAddToReportStep(webDriver,this.testID,
                    "Boutique Rugs Quality Assurance Test",
                    "Test Has Been Completed -- : RESULT : " + testResult.toString() + " ::::::::::: ",
                    ReportStepType.ERROR,
                    reportBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportBuilder.buildReport(this.testID, getReportFilePathWithTestId );

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
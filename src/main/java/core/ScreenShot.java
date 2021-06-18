package core;

import core.environment.EnvironmentUtil;
import core.report.ReportBuilder;
import core.report.model.ReportStep;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import util.ReportStepType;


import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by furkanbrgl on 04/06/2021.
 */
public class ScreenShot{

    private final static Logger LOGGER = Logger.getLogger(ScreenShot.class);

    public static String screenShotsFilePath = EnvironmentUtil.getInstance().getScreenShotsFilePath();

    public static void takeSnapShot(WebDriver webdriver, String name) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        LOGGER.info("ScreenShot was taken");

        File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(screenShotsFilePath);

        FileUtils.copyFile(srcFile, new File(DestFile + System.lineSeparator() +name + ".png"));
        return ;
    }

    public static ReportBuilder takeSnapShotAndAddToReportStep(WebDriver webdriver,
                                          String testId,
                                          String stepHeader,
                                          String stepDescription,
                                          ReportStepType reportStepType,
                                          ReportBuilder reportBuilder) throws Exception{


        File directory = new File(screenShotsFilePath + System.lineSeparator() + testId);
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        String screenShotFullPathWithName = directory.getPath() + "\\" + System.currentTimeMillis() + ".jpg";

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        LOGGER.info("ScreenShot was taken");

        File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(screenShotFullPathWithName));


        ReportStep reportStep = new ReportStep(testId,stepHeader,stepDescription,
                "ActiveURL",
                screenShotFullPathWithName,
                new Date(),
                reportStepType);

        reportBuilder.addStep(reportStep);


        return reportBuilder;
    }
}



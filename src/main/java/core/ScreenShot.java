package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by furkanbrgl on 04/06/2021.
 */
public class ScreenShot {

    private final static Logger LOGGER = Logger.getLogger(ScreenShot.class);

    public static List<File> takeSnapShot(WebDriver webdriver) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        LOGGER.info("ScreenShot was taken");

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
//        File DestFile=new File(fileWithPath);
//        FileUtils.copyFile(SrcFile, DestFile);
        return Arrays.asList(SrcFile);
    }
}



package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class SeleniumUtil {

    final org.apache.log4j.Logger LOGGER = Logger.getLogger(SeleniumUtil.class);

    public static boolean existsElementByXpath(String xPath, WebDriver webDriver) {
        try {
            webDriver.findElement(By.xpath(xPath));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public static boolean existsElementByCssSelector(String css, WebDriver webDriver) {
        try {
            webDriver.findElement(By.cssSelector(css));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

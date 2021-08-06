package util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class WaitTool {

    public static final int DEFAULT_WAIT_4_ELEMENT = 7;
    public static final int DEFAULT_WAIT_4_PAGE = 12;


    public static void waitForPageLoaded(WebDriver driver, int seconds) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, seconds);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail(seconds +" that waited. page did not load.");
        }
    }
    public static WebElement waitForElement(WebDriver driver, final By by,
                                            int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(by));

            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return element; // return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WebElement waitForElementClickable(WebDriver driver,
                                                     final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions.elementToBeClickable(by));

            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return element; // return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WebElement waitForElementPresent(WebDriver driver,
                                                   final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions
                    .presenceOfElementLocated(by));

            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return element; // return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<WebElement> waitForListElementsPresent(WebDriver driver,
                                                              final By by, int timeOutInSeconds) {
        List<WebElement> elements;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until((new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driverObject) {
                    return areElementsPresent(driverObject, by);
                }
            }));

            elements = driver.findElements(by);
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return elements; // return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WebElement waitForElementRefresh(WebDriver driver,
                                                   final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject) {
                    driverObject.navigate().refresh(); // refresh the page
                    // ****************
                    return isElementPresentAndDisplay(driverObject, by);
                }
            });
            element = driver.findElement(by);
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return element; // return the element
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean waitForTextPresent(WebDriver driver, final By by,
                                             final String text, int timeOutInSeconds) {
        boolean isPresent = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent(driverObject, by, text); // is the Text
                    // in the
                    // DOM
                }
            });
            isPresent = isTextPresent(driver, by, text);
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return isPresent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean waitForTextPresent(WebDriver driver,
                                             final String text, int timeOutInSeconds) {
        boolean isPresent = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent(driverObject, text); // is the Text in
                    // the DOM
                }
            });
            isPresent = isTextPresent(driver, text);
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return isPresent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean waitForJavaScriptCondition(WebDriver driver,
                                                     final String javaScript, int timeOutInSeconds) {
        boolean jscondition = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject)
                            .executeScript(javaScript);
                }
            });
            jscondition = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript(javaScript);
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return jscondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean waitForJQueryProcessing(WebDriver driver,
                                                  int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
            // implicitlyWait()
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject)
                            .executeScript("return jQuery.active == 0");
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return jQuery.active == 0");
            driver.manage().timeouts()
                    .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
            // implicitlyWait
            return jQcondition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jQcondition;
    }

    public static void nullifyImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
        // implicitlyWait()
    }

    public static void setImplicitWait(WebDriver driver, int waitTime_InSeconds) {
        driver.manage().timeouts()
                .implicitlyWait(waitTime_InSeconds, TimeUnit.SECONDS);
    }

    public static void resetImplicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
        // implicitlyWait()
        driver.manage().timeouts()
                .implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); // reset
        // implicitlyWait
    }

    public static void resetImplicitWait(WebDriver driver,
                                         int newWaittime_InSeconds) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify
        // implicitlyWait()
        driver.manage().timeouts()
                .implicitlyWait(newWaittime_InSeconds, TimeUnit.SECONDS); // reset
        // implicitlyWait
    }

    private static boolean isTextPresent(WebDriver driver, By by, String text) {
        try {
            return driver.findElement(by).getText().contains(text);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static boolean isTextPresent(WebDriver driver, String text) {
        try {
            return driver.getPageSource().contains(text);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);// if it does not find the element throw
            // NoSuchElementException, which calls
            // "catch(Exception)" and returns false;
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private static boolean areElementsPresent(WebDriver driver, By by) {
        try {
            driver.findElements(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private static boolean isElementPresentAndDisplay(WebDriver driver, By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean waitElementPresentAndDisplay(WebDriver driver, By by,
                                                       int time) {
        try {
            return waitForElementPresent(driver, by, time).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
    }
    public static boolean waitElementDisplay(WebDriver driver, By by, int second){
        boolean validate = false;
        int saniye = 0;
        while(validate==false && saniye <= second){
            validate = driver.findElement(by).isDisplayed();
            sleep(1000);
            saniye = saniye + 1;
        }
        return validate;
    }

    public static void waitElementDisappear(WebDriver driver, By id, int second) {
        int counter = 0;
        while(isElementPresent(driver, id) && counter < (second * 1000)){
            sleep(1000);
            counter += 1000;
        }

    }

    public static boolean waitElementDisappearr(WebDriver driver, By by, int second) {
        boolean validate = false;
        int counter = 0;
        while (validate == false && counter < second) {
            validate = !driver.findElement(by).isDisplayed();
            sleep(1000);
            counter += 1;
        }
        return validate;
    }
}

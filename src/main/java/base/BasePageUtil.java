package base;

import java.util.List;

import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.commons.lang3.StringUtils;
import util.WaitTool;

public class BasePageUtil {
    protected WebDriver driver;
    protected final Logger log = Logger.getLogger(getClass());
    protected WebDriverWait wait;

    public String testID;
    public ReportBuilder reportBuilder;

    public static final int MIN_WAIT = 5;
    public static final int OPT_WAIT = 10;
    public static final int DEFAULT_WAIT = 30;
    public static final int MAX_WAIT = 120;

    public BasePageUtil(WebDriver driver, String testID , ReportBuilder reportBuilder) {
        this.driver = driver;
        this.testID = testID;
        this.reportBuilder = reportBuilder;
    }

    /**
     * Wait for 5 secs to element to appear then submit it.
     */
    public WebElement submitObjectBy(By by) {
        WaitTool.waitForElementClickable(driver, by, MIN_WAIT);
        WebElement element = driver.findElement(by);
        element.submit();
        return element;
    }


    /**
     *
     *
     *
     * Set Value By Different Ways
     *
     *
     *
     */

    /**
     * Sets String Value by BY
     */
    public WebElement setObjectBy(By by, String value) {
        WebElement element = findElement(by);
        element.sendKeys(value);
        return element;
    }

    /**
     * Sets String Value into related field by WebElement.
     */
    public WebElement setElementByActions(WebElement element, String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(text);
        actions.build().perform();
        return element;
    }

    /**
     * Sets Value by Name
     */
    public WebElement setObjectByName(String name, String value) {
        return setObjectBy(By.name(name), value);
    }

    /**
     * Sets Value by ID
     */
    public WebElement setObjectById(String id, String value) {
        return setObjectBy(By.id(id), value);
    }

    /**
     * Sets Value by CSS
     */
    public WebElement setObjectByCssSelector(String cssSelector, String value) {
        return setObjectBy(By.cssSelector(cssSelector), value);
    }

    /**
     * Sets Value by className
     */
    public WebElement setObjectByClassName(String className, String value) {
        return setObjectBy(By.className(className), value);
    }

    /**
     *
     *
     *
     * Select Value By Different Ways
     *
     *
     *
     */

    public WebElement selectValueObjectBy(By by, String value) {
        WebElement element = driver.findElement(by);
        new Select(element).selectByVisibleText(value);
        return element;
    }

    public WebElement selectValueObjectById(String id, String value) {
        return selectValueObjectBy(By.id(id), value);
    }

    public WebElement selectValueObjectByName(String name, String value) {
        return selectValueObjectBy(By.name(name), value);
    }

    public WebElement selectIndexObjectById(String name, int index) {
        WebElement element = driver.findElement(By.id(name));
        new Select(element).selectByIndex(index);
        return element;
    }

    public WebElement selectIndexObjectByName(String name, int index) {
        WebElement element = driver.findElement(By.name(name));
        new Select(element).selectByIndex(index);
        return element;
    }


    /**
     *
     *
     *
     * Click Object By Different Ways
     *
     *
     *
     */

    public WebElement clickObjectById(String id) {
        return clickObjectBy(By.id(id));
    }

    public WebElement clickObjectByCss(String css) {
        return clickObjectBy(By.cssSelector(css));
    }

    public WebElement clickObjectByClassName(String className) {
        return clickObjectBy(By.className(className));
    }

    public WebElement clickObjectByLinkText(String linkText) {
        return clickObjectBy(By.linkText(linkText));
    }

    public WebElement clickObjectByXpath(String xpath) {
        return clickObjectBy(By.xpath(xpath));
    }

    public WebElement clickObjectByPartialLinkText(String linkText) {
        return clickObjectBy(By.partialLinkText(linkText));
    }

    /**
     * Wait for it till it is clickable
     * @param by
     * @return
     */
    public WebElement clickObjectBy(By by) {
        WaitTool.waitForElementClickable(driver, by, DEFAULT_WAIT);
        WebElement element = driver.findElement(by);
        if (!isElementDisplayed(by)) {
            scrollTo(element.getLocation().getX(), element.getLocation().getY());
        }
        try {
            element.click();
        } catch (Exception e) {
            log.info(by + " Element is not clickable.");
        }
        return element;
    }

    /**
     * Wait fot the element till is clickable
     */
    public boolean isClickable(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for it till it is clickable
     */
    public void clickObjectByIndex(By by, int index) {
        WaitTool.waitForElementClickable(driver, by, DEFAULT_WAIT);
        List<WebElement> elementList = driver.findElements(by);
        if (!isElementDisplayed(by)) {
            scrollTo(elementList.get(index).getLocation().getX(), elementList.get(index).getLocation().getY());
        }
        elementList.get(index).click();
    }


    /**
     * If Element Displayed without any waiting timeframe
     *
     */
    protected boolean isElementDisplayed(By by) {
        try {
            return findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * If Element Present without any waiting timeframe
     */
    protected void waitForElementPresent(final WebElement element) {
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                return element != null && element.isDisplayed();
            }
        });
    }

    /**
     * wait for the element till it is clickable
     */
    protected void untilElementClickable(By by) {
        WebElement element = findElement(by);
        untilElementClickable(element);
    }

    /**
     * wait for the element till it is clickable
     */
    protected void untilElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void nullElementException(By by, int... index) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ELEMENT (");
        stringBuilder.append(by);
        stringBuilder.append(",");
        stringBuilder.append(index.length > 0 ? index[0] : "");
        stringBuilder.append(") NOT EXISTS; AUTOMATION DATAS MAY BE INVALID!");
        throw new NullPointerException(stringBuilder.toString());
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public WebElement findElementByAttribute(String attributeName, String attributeValue) {
        return findElement(By.xpath("//*[@" + attributeName + "='" + attributeValue + "']"));
    }

    public List<WebElement> findElementsByAttribute(String attributeName, String attributeValue) {
        return findElements(By.xpath("//*[@" + attributeName + "='" + attributeValue + "']"));
    }

    /**
     * Find Element based on its data-attribute ve data-value
     */
    public By generateXpathAttr(String attributeName, String attributeValue) {
        return By.xpath("//*[@" + attributeName + "='" + attributeValue + "']");
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public boolean isDisplay(By by) {
        return driver.findElement(by).isDisplayed();
    }

    public boolean isDisplayByPassException(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get The Current URL
     */
    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl().trim().toString();
        log.info(currentUrl);
        return currentUrl;
    }

    public void goToUrl(String url) {
        driver.get(url);
    }

    /**
     * Go back previous page.
     */
    public void goBack() {
        driver.navigate().back();
    }

    /**
     * Wait for the desire
     *
     * @param seconds
     */
    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void moveMouse(By by, By validateDisplayWebObject, int count) {
        WaitTool.waitElementPresentAndDisplay(driver, by, 10);
        int i = 0;
        do {
            WebElement we = driver.findElement(by);
            Locatable hoverItem = (Locatable) we;
            Mouse mouse = ((HasInputDevices) driver).getMouse();
            mouse.mouseMove(hoverItem.getCoordinates());
            waitSeconds(1);
            if (count == i++)
                break;
        } while (!isDisplayByPassException(validateDisplayWebObject));

    }

    public void moveMouseWithJQuery(String id, By validateDisplayWebObject, int count) {
        WaitTool.waitElementPresentAndDisplay(driver, By.id(id), 10);
        int i = 0;
        do {
            ((JavascriptExecutor) driver).executeScript("$('" + id + "').mouseover();");
            waitSeconds(1);
            if (count == i++)
                break;
            log.info(i + ". Mouseover TEST ::::::::::");
        } while (!isDisplayByPassException(validateDisplayWebObject));

    }

    public void moveMouseWithJavascript(String id, By validateDisplayWebObject, int count) {
        WaitTool.waitElementPresentAndDisplay(driver, By.id(id), 10);
        int i = 0;
        do {
            ((JavascriptExecutor) driver).executeScript("$('" + id + "').mouseover();");
            String strJavaScript = "var element = arguments[0];"
                    + "var mouseEventObj = document.createEvent('MouseEvents');"
                    + "mouseEventObj.initEvent( 'mouseover', true, true );" + "element.dispatchEvent(mouseEventObj);";

            executeJavascript(strJavaScript, findElement(By.id(id)));
            waitSeconds(1);
            if (count == i++)
                break;
            log.info(i + ". mouseover deneme...");
        } while (!isDisplayByPassException(validateDisplayWebObject));

    }

    public void callPage(String page) {
        driver.get(getCurrentUrl() + page);
    }

    /**
     * Check for the Text if it is present
     */
    public boolean isTextPresent(String text) {
        try {
            return driver.getPageSource().contains(text);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Check for the Element if it is present
     */
    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check for the Text if it is present by WebElement
     */
    public boolean isElementPresent(WebElement element, By by) {
        try {
            element.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check for the Element if it is display
     */
    public boolean isElementPresentAndDisplay(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void javaScriptClicker(WebDriver driver, WebElement element) {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);", element);
    }

    /**
     * Wait for the element till it is presence on the screen
     */
    public WebElement waitForElement(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 1000);
        return wait.until(ExpectedConditions.presenceOfElementLocated(elementBy));
    }

    /**
     * Make the related element display on the screen and locates it bottom of the page
     */
    public void moveToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        String browserName = System.getProperty("browser");
        log.info("Browser Parameter = " + browserName);
        if (!StringUtils.isEmpty(browserName) && browserName.contains("firefox")) {
            findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.UP, Keys.UP));
        }
    }

    /**
     * Make the related element display on the screen and locates it bottom of the page
     */
    public void moveToBy(By by) {
        WebElement element = findElement(by);
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        String browserName = System.getProperty("browser");
        log.info("Browser Parameter = " + browserName);
        if (!StringUtils.isEmpty(browserName) && browserName.contains("firefox")) {
            findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.UP, Keys.UP));
        }
    }

    /**
     * Move the page up
     */
    protected void keysUp() {
        findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.UP, Keys.UP, Keys.UP));
    }

    /**
     * Move the page down
     */
    protected void keysDown() {
        findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN));
    }

    /**
     * Scroll the related element and locates it top side of the page
     */
    protected void scrollToElement(WebElement element) {
        if (element != null) {
            scrollTo(element.getLocation().getX(), element.getLocation().getY());
        }
    }

    /**
     * Scroll the related element and locates it top side of the page
     */
    protected void scrollToBy(By by) {
        WebElement element = findElement(by);
        if (element != null) {
            scrollTo(element.getLocation().getX(), element.getLocation().getY());
        }
    }

    protected void scrollTo(int x, int y) {
        String jsStmt = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(jsStmt, true);
    }

    /**
     * Got to the bottom of the page
     */
    protected void scrollToPageEnd() {
        executeJS("window.scrollTo(0, document.body.scrollHeight)", true);
    }

    /**
     * Go to the top of the page
     */
    protected void scrollToPageUp() {
        executeJS("window.scrollTo(document.body.scrollHeight, 0)", true);
    }

    protected Object executeJS(String jsStmt, boolean wait) {
        return wait ? getJSExecutor().executeScript(jsStmt, "") : getJSExecutor().executeAsyncScript(jsStmt, "");
    }

    protected JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }

    /**
     * Execute Javascript
     */
    public void executeJavascript(String script, Object... obj) {
        ((JavascriptExecutor) driver).executeScript(script, obj);
    }

    /**
     * Mouse Hover
     */
    public void hoverToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    /**
     * Check if the element exist withing the timeframe
     */
    public boolean isExistElement(int sec, By by) {
        try {
            waitForElement(sec, by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExistElementList(int sec, By by) {
        try {
            waitForElements(sec, by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExistElementContains(int sec, By by, String str) {
        return waitForElement(sec, by).getText().contains(str);
    }

    public boolean isExistElementEquals(int sec, By by, String str) {
        return waitForElement(sec, by).getText().trim().equals(str);
    }

    public String getText(By by) {
        return waitForElement(DEFAULT_WAIT, by).getText();
    }

    public String getTextFromList(By by, int index) {
        return waitForElements(DEFAULT_WAIT, by).get(index).getText();
    }

    protected String getTextWithWait(int second, By by) {
        return waitForElement(second, by).getText();
    }

    protected String getTextWithWaitIndex(int second, int index, By by) {
        return waitForElements(second, by).get(index).getText();
    }

    public void selectOptionClickByText(By by, String txt) {
        Select dropdown = new Select(findElement(by));
        dropdown.selectByVisibleText(txt);
    }

    public void selectOptionClickByValue(By by, String value) {
        Select dropdown = new Select(findElement(by));
        dropdown.selectByValue(value);
    }

    public WebElement selectOptionFirstSelect(By by) {
        Select dropdown = new Select(findElement(by));
        return dropdown.getFirstSelectedOption();
    }

    public List<WebElement> waitForElements(int seconds, By elementBy) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, 30);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementBy));
    }

    /**
     * wait for the element till it disappears
     */
    protected boolean untilElementDisappearBy(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * wait for the element till it disappears
     */
    protected void untilElementDisappear(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Expects the element appears default waiting time 30 secs
     */
    protected boolean untilElementAppearBy(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * iFrame Transition
     */
    public void switchToiFrame(String attribute, String attributeValue) {
        if (isElementPresent(By.tagName("iframe"))) {
            List<WebElement> iframeList = findElements(By.tagName("iframe"));
            for (WebElement webElement : iframeList) {
                if (webElement.getAttribute(attribute).contains(attributeValue)) {
                    driver.switchTo().frame(webElement);
                    log.info(driver.getWindowHandle());
                    break;
                }
            }
        }
    }

    /**
     * iFrame Transition
     */
    public void switchToiFrameBy(By byCss) {
        driver.switchTo().frame(findElement(byCss));
    }

    public WebElement getElementBy(By by) {
        return driver.findElement(by);
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

}

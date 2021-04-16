package util.email;

import core.Environment;
import util.DateUtil;
import util.TestIdGenerator;
import util.TestResult;

import java.util.Date;

public class ContentBuilder {

    private String testStartTime;
    private String testID;

    private String testFinishTime;
    private String testDuration;
    private String testResult;
    private long testStartMillis;

    public String getTestStartTime() {
        return testStartTime;
    }

    public void setTestStartTime(String testStartTime) {
        this.testStartTime = testStartTime;
    }

    public String getTestFinishTime() {
        return testFinishTime;
    }

    public void setTestFinishTime(String testFinishTime) {
        this.testFinishTime = testFinishTime;
    }

    public String getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(String testDuration) {
        this.testDuration = testDuration;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public long getTestStartMillis() {
        return testStartMillis;
    }

    public void setTestStartMillis(long testStartMillis) {
        this.testStartMillis = testStartMillis;
    }

    public void setUpInitialContentParameters() {

        this.setTestID(TestIdGenerator.generate(Environment.PROD));
        this.setTestStartTime(DateUtil.formatDateWithTime(new Date()));
        this.setTestStartMillis(System.currentTimeMillis());
        this.setTestResult(TestResult.getTestResult());
    }


    public void setUpResultParameters(){
        this.setTestFinishTime(DateUtil.formatDateWithTime(new Date()));
        this.setTestResult(TestResult.getTestResult());

        long diff = System.currentTimeMillis() - this.getTestStartMillis();
        this.setTestDuration(DateUtil.formatDateWithTime(new Date(diff)));
    }

    /**
     * TODO: will be developed as HTML generator page. furkan
     * @return
     */
    public String getHTMLContent() {

        String c = "Test ID : " + getTestID() + System.getProperty("line.separator") +
                    "Test Start Time : " + getTestStartTime() + System.getProperty("line.separator") +
                    "Test Finish Time : " + getTestFinishTime() + System.getProperty("line.separator") +
                    "Test Duration : " + getTestDuration() + System.getProperty("line.separator") +
                    "Test Result Status : " + getTestResult() + System.getProperty("line.separator");
        return c;
    }
}

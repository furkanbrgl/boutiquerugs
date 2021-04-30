/**
 *
 */
package core.report.model;


import util.ReportStepType;

import java.util.Date;

/**
 * @author Furkan Birgul
 */
public class ReportStep {

    private String testId;
    private String stepHeader;
    private String stepDescription;
    private String activeUrl;
    private Date time;
    private String screenShot;
    private ReportStepType reportStepType = ReportStepType.INFO;

    /**
     * @param testId
     * @param stepHeader
     * @param stepDescription
     * @param activeUrl
     */
    public ReportStep(String testId, String stepHeader, String stepDescription, String activeUrl, String screenShot,
                      Date time) {
        super();
        this.testId = testId;
        this.stepHeader = stepHeader;
        this.stepDescription = stepDescription;
        this.activeUrl = activeUrl;
        this.screenShot = screenShot;
        this.time = time;
    }

    /**
     * @param testId
     * @param stepHeader
     * @param stepDescription
     * @param activeUrl
     * @param screenShot
     * @param time
     * @param reportStepType
     */
    public ReportStep(String testId, String stepHeader, String stepDescription, String activeUrl, String screenShot,
                      Date time, ReportStepType reportStepType) {
        super();
        this.testId = testId;
        this.stepHeader = stepHeader;
        this.stepDescription = stepDescription;
        this.activeUrl = activeUrl;
        this.screenShot = screenShot;
        this.time = time;
        this.reportStepType = reportStepType;
    }

    /**
     * @return the testId
     */
    public String getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public void setTestId(String testId) {
        this.testId = testId;
    }

    /**
     * @return the stepHeader
     */
    public String getStepHeader() {
        return stepHeader;
    }

    /**
     * @param stepHeader the stepHeader to set
     */
    public void setStepHeader(String stepHeader) {
        this.stepHeader = stepHeader;
    }

    /**
     * @return the stepDescription
     */
    public String getStepDescription() {
        return stepDescription;
    }

    /**
     * @param stepDescription the stepDescription to set
     */
    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    /**
     * @return the activeUrl
     */
    public String getActiveUrl() {
        return activeUrl;
    }

    /**
     * @param activeUrl the activeUrl to set
     */
    public void setActiveUrl(String activeUrl) {
        this.activeUrl = activeUrl;
    }

    /**
     * @return the screenShot
     */
    public String getScreenShot() {
        return screenShot;
    }

    /**
     * @param screenShot the screenShot to set
     */
    public void setScreenShot(String screenShot) {
        this.screenShot = screenShot;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    public ReportStepType getReportStepType() {
        return reportStepType;
    }

    public void setReportStepType(ReportStepType reportStepType) {
        this.reportStepType = reportStepType;
    }

    @Override
    public String toString() {
        return "ReportStep [testId=" + testId + ", stepHeader=" + stepHeader + ", stepDescription=" + stepDescription
                + ", activeUrl=" + activeUrl + ", time=" + time + ", screenShot=" + screenShot + "]";
    }


}

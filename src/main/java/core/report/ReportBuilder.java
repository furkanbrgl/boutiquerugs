/**
 *
 */
package tr.com.turkcell.sahi.core.report;

import tr.com.turkcell.sahi.core.enums.ReportStepType;
import tr.com.turkcell.sahi.core.report.model.Report;
import tr.com.turkcell.sahi.core.report.model.ReportHeader;
import tr.com.turkcell.sahi.core.report.model.ReportStep;

/**
 * @author Onur Erdogan
 */
public interface ReportBuilder {


    /**
     * @param header
     */
    public void addHeader(ReportHeader header);

    /**
     * @param step
     */
    public void addStep(ReportStep step);

    /**
     * @param step
     * @param reportStepType
     */
    public void addStep(ReportStep step, ReportStepType reportStepType);

    /**
     * @param testId
     * @param path
     */
    public void buildReport(String testId, String path);

    /**
     * @param testId
     * @return
     */
    public Report gatherReport(String testId);

}

package core.report;

import core.report.model.Report;
import core.report.model.ReportHeader;
import core.report.model.ReportStep;
import util.ReportStepType;

/**
 * @author Furkan Birgul
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

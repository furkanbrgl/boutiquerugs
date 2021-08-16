package core.report;

import core.report.model.Report;
import core.report.model.ReportHeader;
import core.report.model.ReportStep;
import util.ReportStepType;

/**
 * @author Furkan Birgul
 */
public class Reporter implements ReportBuilder {

    public ReportBuilder reportBuilder = new ReportBuilderWord();


    @Override
    public void addHeader(ReportHeader header) {
        reportBuilder.addHeader(header);

    }

    @Override
    public ReportHeader getHeader() {
        return reportBuilder.getHeader();
    }

    @Override
    public void addStep(ReportStep step) {
        reportBuilder.addStep(step);
    }


    @Override
    public void addStep(ReportStep step, ReportStepType reportStepType) {
        reportBuilder.addStep(step, reportStepType);
    }

    @Override
    public void buildReport(String testId, String path) {
        reportBuilder.buildReport(testId, path);

    }
    @Override
    public Report gatherReport(String testId) {
        return reportBuilder.gatherReport(testId);
    }

}

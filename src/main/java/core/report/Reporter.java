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
public class Reporter implements ReportBuilder {

    public ReportBuilder reportBuilder = new ReportBuilderWord();

    /*
     * (non-Javadoc)
     *
     * @see
     * tr.com.turkcell.sahi.core.report.ReportBuilder#addHeader(tr.com.turkcell.
     * sahi.core.report.model.ReportHeader)
     */
    @Override
    public void addHeader(ReportHeader header) {
        reportBuilder.addHeader(header);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * tr.com.turkcell.sahi.core.report.ReportBuilder#addStep(tr.com.turkcell.
     * sahi.core.report.model.ReportStep)
     */
    @Override
    public void addStep(ReportStep step) {
        reportBuilder.addStep(step);
    }

    /**
     * @param step
     * @param reportStepType
     */
    @Override
    public void addStep(ReportStep step, ReportStepType reportStepType) {
        reportBuilder.addStep(step, reportStepType);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * tr.com.turkcell.sahi.core.report.ReportBuilder#buildReport(java.lang.
     * String, java.lang.String)
     */
    @Override
    public void buildReport(String testId, String path) {
        reportBuilder.buildReport(testId, path);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * tr.com.turkcell.sahi.core.report.ReportBuilder#gatherReport(java.lang.
     * String)
     */
    @Override
    public Report gatherReport(String testId) {
        return reportBuilder.gatherReport(testId);
    }

}

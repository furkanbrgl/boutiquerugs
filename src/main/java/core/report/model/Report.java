/**
 * 
 */
package tr.com.turkcell.sahi.core.report.model;

import java.util.Date;
import java.util.List;

/**
 * @author Onur Erdogan
 *
 */
public class Report {

	private String testId;
	private Date startTime;
	private Date finishTime;
	private ReportHeader reportHeader;
	private List<ReportStep> reportSteps;

	/** 
	 * Default constructor.
	 */
	public Report() {
		super();
	}

	/**
	 * @param testId
	 */
	public Report(String testId) {
		super();
		this.testId = testId;
	}

	/**
	 * @param testId
	 * @param startTime
	 * @param reportHeader
	 * @param reportSteps
	 */
	public Report(String testId, Date startTime, ReportHeader reportHeader, List<ReportStep> reportSteps) {
		super();
		this.testId = testId;
		this.startTime = startTime;
		this.reportHeader = reportHeader;
		this.reportSteps = reportSteps;
	}

	/**
	 * @return the testId
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 *            the testId to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime
	 *            the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the reportHeader
	 */
	public ReportHeader getReportHeader() {
		return reportHeader;
	}

	/**
	 * @param reportHeader
	 *            the reportHeader to set
	 */
	public void setReportHeader(ReportHeader reportHeader) {
		this.reportHeader = reportHeader;
	}

	/**
	 * @return the reportSteps
	 */
	public List<ReportStep> getReportSteps() {
		return reportSteps;
	}

	/**
	 * @param reportSteps
	 *            the reportSteps to set
	 */
	public void setReportSteps(List<ReportStep> reportSteps) {
		this.reportSteps = reportSteps;
	}

	@Override
	public String toString() {
		return "Report [testId=" + testId + ", startTime=" + startTime + ", finishTime=" + finishTime + "]";
	}

}

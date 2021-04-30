/**
 * 
 */
package tr.com.turkcell.sahi.core.report.model;

import java.util.Date;
import java.util.Map;

import tr.com.turkcell.sahi.core.enums.Environment;

/**
 * 
 * This class represent report header.
 * 
 * @author Onur Erdoğan
 *
 */
public class ReportHeader {

	private String testId;
	private String header;
	private String user;
	private Date startTime;
	private Date finishTime;
	private Environment environment;
	private Map<String, String> usedParameters;

	public ReportHeader(String testId, String header, String user, Date startTime, Date finishTime,
			Environment environment, Map<String, String> usedParameters) {
		super();
		this.testId = testId;
		this.header = header;
		this.user = user;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.environment = environment;
		this.usedParameters = usedParameters;
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
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * @return the usedParameters
	 */
	public Map<String, String> getUsedParameters() {
		return usedParameters;
	}

	/**
	 * @param usedParameters
	 *            the usedParameters to set
	 */
	public void setUsedParameters(Map<String, String> usedParameters) {
		this.usedParameters = usedParameters;
	}

	@Override
	public String toString() {
		return "ReportHeader [testId=" + testId + ", header=" + header + ", user=" + user + ", startTime=" + startTime
				+ ", finishTime=" + finishTime + ", environment=" + environment + "]";
	}

}

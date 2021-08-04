package base;

import core.report.ReportBuilder;
import org.openqa.selenium.WebDriver;

public abstract class BRWebDriver implements WebDriver {

    public String testId;
    public ReportBuilder reportBuilder;
}

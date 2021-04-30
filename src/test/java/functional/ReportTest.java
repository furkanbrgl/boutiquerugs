package functional;

import core.environment.Environment;
import core.report.ReportBuilder;
import core.report.ReportBuilderWord;
import core.report.Reporter;
import core.report.model.ReportHeader;
import core.report.model.ReportStep;
import org.junit.Test;
import core.environment.EnvironmentUtil;
import util.ReportStepType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportTest {

    @Test
    public void ReportTest() {

        ReportBuilder reportBuilder = new ReportBuilderWord();


        ReportHeader reportHeader = new ReportHeader("TestID",
                "HeaderHeader", "UserHeader",
                new Date(), new Date() ,
                Environment.DEV,new HashMap<String, String>());

        reportBuilder.addHeader(reportHeader);


        ReportStep reportStep = new ReportStep("TestID",
                "StepHeader",
                "StepDescrepiton",
                "ActiveURL",
                "C:\\Users\\brglf\\OneDrive\\Desktop\\New folder\\furkanbirgul.jpg",
                new Date(),
                ReportStepType.INFO);

        reportBuilder.addStep(reportStep);

        ReportStep reportStep2 = new ReportStep("TestID",
                "StepHeader2",
                "StepDescrepiton2",
                "ActiveURL2",
                "C:\\Users\\brglf\\OneDrive\\Desktop\\New folder\\furkanbirgul.jpg",
                new Date(),
                ReportStepType.WARN);

        reportBuilder.addStep(reportStep);


        reportBuilder.buildReport("TestID", "C:\\Users\\brglf\\OneDrive\\Desktop\\New folder\\TestID.docx");

    }
}

package core.report;

import core.report.model.Report;
import core.report.model.ReportHeader;
import core.report.model.ReportStep;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import util.DateUtil;
import util.PasswordUtil;
import util.ReportColor;
import util.ReportStepType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Furkan Birgul
 */
public class ReportBuilderWord implements ReportBuilder {

    private final static Logger logger = Logger.getLogger(ReportBuilderWord.class);

    FileOutputStream out = null;
    FileInputStream fis = null;

    private static List<ReportHeader> reportHeaders = new ArrayList<ReportHeader>();
    private static List<ReportStep> reportSteps = new ArrayList<ReportStep>();

    private final String fontFamily = "Times New Roman";

    @Override
    public void addHeader(ReportHeader header) {
        reportHeaders.add(header);
    }

    @Override
    public void addStep(ReportStep step) {
        reportSteps.add(step);

    }

    @Override
    public void addStep(ReportStep step, ReportStepType reportStepType) {
        step.setReportStepType(reportStepType);
        reportSteps.add(step);
    }

    @Override
    public void buildReport(String testId, String path) {
        String testResultKey = "TestResult";
        try {
            Report report = this.gatherReport(testId);

            report.getFinishTime();

            // HEADER [BEGIN]
            XWPFDocument doc = new XWPFDocument();

            XWPFParagraph header = doc.createParagraph();
            header.setAlignment(ParagraphAlignment.CENTER);

            header.setBorderBottom(Borders.DOUBLE);
            header.setBorderTop(Borders.DOUBLE);
            header.setBorderRight(Borders.DOUBLE);
            header.setBorderLeft(Borders.DOUBLE);

            header.setVerticalAlignment(TextAlignment.TOP);

            XWPFRun rHeader = header.createRun();
            rHeader.setBold(true);
            rHeader.setText(report.getReportHeader().getHeader());
            rHeader.setFontFamily(fontFamily);
            rHeader.setFontSize(20);
            // HEADER [END]

            // HEADER DETAILS [BEGIN]
            XWPFParagraph headerDetails = doc.createParagraph();
            headerDetails.setAlignment(ParagraphAlignment.LEFT);

            headerDetails.setBorderTop(Borders.SINGLE);
            headerDetails.setBorderBottom(Borders.SINGLE);
            headerDetails.setBorderRight(Borders.SINGLE);
            headerDetails.setBorderLeft(Borders.SINGLE);

            XWPFRun rHeaderDetails = headerDetails.createRun();

            rHeaderDetails.setText("Test ID : " + report.getReportHeader().getTestId());
            rHeaderDetails.addBreak();

            rHeaderDetails.setText(
                    "Start Time : " + DateUtil.formatDateWithTime(report.getReportHeader().getStartTime()));
            rHeaderDetails.addBreak();

            /**
             * TODO: username, used paramaters, and Env info will be added to report. 05/01/2021
             */
            //will be fixed
            rHeaderDetails
                    .setText("Finish Time : " + DateUtil.formatDateWithTime(report.getReportHeader().getFinishTime()));
            rHeaderDetails.addBreak();

            //will be fixed
            if (report.getReportHeader().getUsedParameters().containsKey(testResultKey)) {
                String testStatus = report.getReportHeader().getUsedParameters().get(testResultKey);
                rHeaderDetails.setText("Test Status : " + testStatus);
                report.getReportHeader().getUsedParameters().remove(testResultKey);
            }

            rHeaderDetails.addBreak();
            // HEADER DETAILS [END]

            // USED PARAMETERS [BEGIN]
            XWPFParagraph userParameters = doc.createParagraph();
            userParameters.setAlignment(ParagraphAlignment.LEFT);

            userParameters.setBorderTop(Borders.SINGLE);
            userParameters.setBorderBottom(Borders.SINGLE);
            userParameters.setBorderRight(Borders.SINGLE);
            userParameters.setBorderLeft(Borders.SINGLE);

            XWPFRun rUserParametersHeader = userParameters.createRun();
            rUserParametersHeader.setText("Used Parameters");
            rUserParametersHeader.setBold(true);
            rUserParametersHeader.setFontSize(18);
            rUserParametersHeader.setUnderline(UnderlinePatterns.SINGLE);
            rUserParametersHeader.addBreak();

            XWPFRun rUserParameters = userParameters.createRun();

            logger.info("Used parameters ...");
            if (report.getReportHeader().getUsedParameters() != null) {
                for (Map.Entry<String, String> entry : report.getReportHeader().getUsedParameters().entrySet()) {

                    logger.info(entry.getKey() + "/" + entry.getValue());

                    if (PasswordUtil.isContainsPassword(entry.getKey())) {
                        rUserParameters.setText(entry.getKey() + " / \"" + PasswordUtil.stringMasking(entry.getValue(), '*') + "\"");
                    } else {
                        rUserParameters.setText(entry.getKey() + " / \"" + entry.getValue() + "\"");
                    }

                    rUserParameters.addBreak();
                }
            } else {
                logger.warn("Used parameters map is null or empty");
            }

            rUserParameters.addBreak(BreakType.PAGE);

            // USED PARAMETERS [END]

            // STEPS [BEGIN]
            XWPFParagraph stepsHeader = doc.createParagraph();
            stepsHeader.setAlignment(ParagraphAlignment.CENTER);

            stepsHeader.setBorderTop(Borders.DOUBLE);
            stepsHeader.setBorderBottom(Borders.DOUBLE);
            stepsHeader.setBorderRight(Borders.DOUBLE);
            stepsHeader.setBorderLeft(Borders.DOUBLE);

            XWPFRun rStepsMainHeader = stepsHeader.createRun();
            rStepsMainHeader.setText("Scenario Steps");
            rStepsMainHeader.setBold(true);
            rStepsMainHeader.setFontSize(18);
            rStepsMainHeader.addBreak();


            logger.info("Report steps adding to report.");

            for (ReportStep rs : report.getReportSteps()) {

                XWPFParagraph steps = doc.createParagraph();

                steps.setAlignment(ParagraphAlignment.LEFT);

                steps.setBorderTop(Borders.SINGLE);
                steps.setBorderBottom(Borders.SINGLE);
                steps.setBorderRight(Borders.SINGLE);
                steps.setBorderLeft(Borders.SINGLE);

                steps.setBorderBetween(Borders.SINGLE);

                XWPFRun rStepsHeader = steps.createRun();

                logger.info(rs.toString());

                if (rs.getReportStepType() == ReportStepType.ERROR) {
                    rStepsHeader.setText("ERROR : " + rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                    rStepsHeader.setColor(ReportColor.RED.getValue());
                } else if (rs.getReportStepType() == ReportStepType.WARN) {
                    rStepsHeader.setText("WARNING : " + rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                    rStepsHeader.setColor(ReportColor.YELLOW.getValue());
                } else if (rs.getReportStepType() == ReportStepType.SUCCESS) {
                    rStepsHeader.setText("SUCCESS : " + rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                    rStepsHeader.setColor(ReportColor.GREEN.getValue());
                }  else {
                    rStepsHeader.setText(rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                }

                XWPFRun rSteps = steps.createRun();

                rSteps.setText("Action Time : " + DateUtil.formatDateWithTime(rs.getTime()));
                rSteps.setBold(false);
                rSteps.addCarriageReturn();

                if (rs.getStepDescription() != null) {
                    if (rs.getStepDescription().contains("\n")) {
                        String[] lines = rs.getStepDescription().split("\n");
                        rSteps.setText(lines[0], 0); // set first line into XWPFRun
                        for (int i = 1; i < lines.length; i++) {
                            // add break and insert new text
                            rSteps.addBreak();
                            rSteps.setText(lines[i]);
                        }
                    } else {
                        rSteps.setText(rs.getStepDescription());
                    }
                }

                rSteps.setBold(false);
                rSteps.addCarriageReturn();

                if (rs.getScreenShot() != null) {

                    File screenShot = new File(rs.getScreenShot());

                    if (screenShot != null && screenShot.isFile()) {
                        fis = new FileInputStream(screenShot);

                        rSteps.addPicture(fis, Document.PICTURE_TYPE_PNG, screenShot.getName(), Units.toEMU(450),
                                Units.toEMU(500));
                    } else {
                        rSteps.setText("ERROR : FILE OR FOLDER DO NOT EXIST " + rs.getScreenShot());
                    }
                    rSteps.addCarriageReturn();
                }

                rSteps.addBreak(BreakType.PAGE);
            }
            // USED PARAMETERS [END]

            File file = new java.io.File(path);
            file.getParentFile().mkdirs(); // correct!
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(path);
            doc.write(out);

        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } catch (InvalidFormatException e) {
            logger.error(e);
        } finally {
            try {
                out.close();
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }

    }

    public Report gatherReport(String testId) {

        logger.info("Report is gathering ...");
        Report report = new Report(testId);

        List<ReportStep> testSteps = new ArrayList<ReportStep>();

        for (ReportHeader rh : reportHeaders) {
            if (rh.getTestId().equals(testId)) {
                report.setReportHeader(rh);
                break;
            }
        }

        for (ReportStep rp : reportSteps) {
            if (rp.getTestId().equals(testId)) {
                testSteps.add(rp);
            }
        }

        report.setReportSteps(testSteps);

        return report;
    }

}

/**
 *
 */
package tr.com.turkcell.sahi.core.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import tr.com.turkcell.sahi.core.enums.ReportColor;
import tr.com.turkcell.sahi.core.enums.ReportStepType;
import tr.com.turkcell.sahi.core.report.model.Report;
import tr.com.turkcell.sahi.core.report.model.ReportHeader;
import tr.com.turkcell.sahi.core.report.model.ReportStep;
import tr.com.turkcell.sahi.util.DateUtil;
import tr.com.turkcell.sahi.util.PasswordUtil;

/**
 * @author Onur Erdogan
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

            rHeaderDetails.setText("Test Numarası : " + report.getReportHeader().getTestId());
            rHeaderDetails.addBreak();

            rHeaderDetails.setText(
                    "Başlangıç Zamanı : " + DateUtil.formatDateWithTime(report.getReportHeader().getStartTime()));
            rHeaderDetails.addBreak();

            rHeaderDetails
                    .setText("Bitiş Zamanı : " + DateUtil.formatDateWithTime(report.getReportHeader().getFinishTime()));
            rHeaderDetails.addBreak();

            if (report.getReportHeader().getUsedParameters().containsKey(testResultKey)) {
                String testStatus = report.getReportHeader().getUsedParameters().get(testResultKey);
                rHeaderDetails.setText("Test Durumu : " + testStatus);
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
            rUserParametersHeader.setText("Senaryoda Kullanılan Parametreler");
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
            rStepsMainHeader.setText("Senaryoda Adımları");
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
                    rStepsHeader.setText("HATA : " + rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                    rStepsHeader.setColor(ReportColor.RED.getValue());
                } else if (rs.getReportStepType() == ReportStepType.WARN) {
                    rStepsHeader.setText("UYARI : " + rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                    rStepsHeader.setColor(ReportColor.YELLOW.getValue());
                } else {
                    rStepsHeader.setText(rs.getStepHeader());
                    rStepsHeader.setBold(true);
                    rStepsHeader.addCarriageReturn();
                }

                XWPFRun rSteps = steps.createRun();

                rSteps.setText("İşleme Zamanı : " + DateUtil.formatDateWithTime(rs.getTime()));
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
                        rSteps.setText("HATA : DOSYA BULUNAMADI " + rs.getScreenShot());
                    }
                    rSteps.addCarriageReturn();
                }

                rSteps.addBreak(BreakType.PAGE);
            }
            // USED PARAMETERS [END]

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

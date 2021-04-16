package functional;

import core.EmailSender;
import core.test.TestStatus;
import core.email.ConfigConsts;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SendEmailWithAttachments {

    public static void main(String[] args) {


        // Get all files in Folder
        File filesInPath = new File(ConfigConsts.FILE_PATH);
        List<File> listOfFlies = Arrays.asList(filesInPath.listFiles());

        try {
            EmailSender.sendEmail(TestStatus.SUCCESSFUL.name(), "body content", listOfFlies);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
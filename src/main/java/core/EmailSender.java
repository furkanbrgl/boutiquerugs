package core;

import core.environment.EnvironmentUtil;
import org.apache.log4j.Logger;
import core.email.ConfigConsts;
import core.email.MailMessageUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    static final Logger LOGGER = Logger.getLogger(EmailSender.class);


    public static void sendEmail(String emailSubject, String bodyContent, List<File> listOfAttachments) throws MessagingException {

        Message message = new MimeMessage(getSession());

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(EnvironmentUtil.getInstance().getFromEmailAddress()));
        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(EnvironmentUtil.getInstance().getTestResultEmailAddress()));
        // Set Subject: header field
        message.setSubject(emailSubject);

        // Get Multipart Object with Message Body and Attachments
        Multipart multipart = MailMessageUtils.messageMultipart(listOfAttachments, bodyContent);

        // Set the multiplart object (message body + files) to the message object
        message.setContent(multipart);

        Transport.send(message);
        LOGGER.info("Email message was sent");

    }

    public static void sendEmail(String emailSubject, String bodyContent) throws MessagingException {

        Message message = new MimeMessage(getSession());

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(EnvironmentUtil.getInstance().getFromEmailAddress()));
        // Set To: header field of the header.
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(EnvironmentUtil.getInstance().getTestResultEmailAddress()));
        // Set Subject: header field
        message.setSubject(emailSubject);

        // Get Multipart Object with Message Body and Attachments
        Multipart multipart = MailMessageUtils.messageMultipart(null, bodyContent);

        // Set the multiplart object (message body + files) to the message object
        message.setContent(multipart);

        Transport.send(message);
        LOGGER.info("Email message was sent");

    }

    private static Session getSession() {

        // Set Java Mail Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", ConfigConsts.SMPT_HOST_ADDRESS);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", ConfigConsts.SMPT_HOST_ADDRESS);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EnvironmentUtil.getInstance().getFromEmailAddress(),
                                EnvironmentUtil.getInstance().getEmailUserPassword());
                    }
                });

        return session;
    }

}

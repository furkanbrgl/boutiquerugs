package util.email;

public final class ConfigConsts {

    // Physical Folder Path
    public static final String FILE_PATH = "C:\\Users\\brglf\\OneDrive\\Desktop\\plag";

    // Set SMTP Server Address - GMAIL, OUTLOOK, YAHOO or ZOHO
    public static final String SMPT_HOST_ADDRESS = MailServers.GMAIL.getAddress();

    // Server name - gets reflected in email message Subject and body
    public static final String SMPT_HOST_NAME = MailServers.GMAIL.getName();

}

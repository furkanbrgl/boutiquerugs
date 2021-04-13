package util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by furkanbrgl on 04/04/2021.
 */
public class EnviromentUtil {

    private final static String devPropertyName = "dev.environment.properties";
    private final static String prodPropertyName = "prod.environment.properties";

    private final static Logger LOGGER = Logger.getLogger(EnviromentUtil.class);

    private static EnviromentUtil instance = new EnviromentUtil();
    public static EnviromentUtil getInstance() {
        return instance;
    }

    private EnviromentUtil() {
        this.loadToSystemFile();
    }

    private void loadToSystemFile() {

        String propertyFile;

        if(isEnvironmentProd()){
            propertyFile = this.prodPropertyName;
        } else {
            propertyFile = this.devPropertyName;
        }

        Properties properties = System.getProperties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(propertyFile));
            LOGGER.info("Environment properties loaded to System");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getResourceBaseURL() {
        return System.getProperty("resource.base.url");
    }


    public String getChromeDriver() {
        return System.getProperty("chrome.driver");
    }

    public String getChromeDriverPath() {
        return System.getProperty("chrome.path");
    }

    public String getBrEmail() {
        return System.getProperty("boutique.rugs.user.email");
    }

    public String getBrPassword() {
        this.isEnvironmentProd();
        return System.getProperty("boutique.rugs.user.password");
    }
    // Recipient's email ID needs to be mentioned.
    public String getTestResultEmailAddress() {
        return System.getProperty("test.result.email.address");
    }
    // Sender's email ID needs to be mentioned and Email address for Login
    public String getFromEmailAddress() {
        return System.getProperty("from.email.address");
    }
    // Email password for Login
    public String getEmailUserPassword() {
        return System.getProperty("email.user.password");
    }

    private boolean isEnvironmentProd(){
        if (System.getProperty("env") == null) {
            LOGGER.info("Enviroment is DEV !!");
            return false;
        } else {
            LOGGER.info("Enviroment is PROD !!");
            return true;
        }
    }
}
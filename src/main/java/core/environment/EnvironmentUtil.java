package core.environment;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by furkanbrgl on 04/04/2021.
 */
public class EnvironmentUtil {

    private final static String devPropertyName = "dev.environment.properties";
    private final static String prodPropertyName = "prod.environment.properties";

    private final static Logger LOGGER = Logger.getLogger(EnvironmentUtil.class);

    private static EnvironmentUtil instance = new EnvironmentUtil();
    public static EnvironmentUtil getInstance() {
        return instance;
    }

    private EnvironmentUtil() {
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
            if(isEnvironmentProd()){
                properties.load(new FileInputStream(System.getProperty("prod.config.path")));
            } else{
                properties.load(getClass().getClassLoader().getResourceAsStream(propertyFile));
            }
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

    public String getScreenShotsFilePath() {
        return System.getProperty("screenshots.file.path");
    }

    public String getReportFilePath() {
        return System.getProperty("report.file.path");

    }

    public String getTestId() {
        return System.getProperty("test.id");
    }

    public String getTestName() {
        return System.getProperty("test");
    }

    public String getBrEmail() {
        return System.getProperty("boutique.rugs.user.email");
    }

    public String getBrPassword() {
        this.isEnvironmentProd();
        return System.getProperty("boutique.rugs.user.password");
    }

    private boolean isEnvironmentProd(){
        if(System.getProperty("env") != null) {
            if (System.getProperty("env").equals(Environment.DEV.name())) {
                LOGGER.info("Enviroment is DEV !!");
                return false;
            } else {
                LOGGER.info("Enviroment is PROD !!");
                return true;
            }
        } else {
            LOGGER.info("Enviroment is DEV !!");
            return false;
        }
    }

}
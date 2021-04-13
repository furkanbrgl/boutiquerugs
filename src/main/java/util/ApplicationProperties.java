package util;

import org.apache.log4j.Logger;

public class ApplicationProperties {
/*
    private final static Logger logger = Logger.getLogger(ApplicationProperties.class);

    private final static String filename = "application.properties";

    private static Properties prop = null;
    private static InputStream input = null;


    private static void loadProperties() throws Exception {

        input = ApplicationProperties.class.getClassLoader().getResourceAsStream(filename);
        if (input == null) {
            throw new Exception("Sorry, unable to find " + filename);
        }

        prop = new Properties();
        prop.load(input);

    }

    /**
     * Read the property from properties by key.
     * It just load properties first usage of method so, first usage may take longer time than next usages.
     *
     * @param key
     * @return
     * @throws Exception

    public static String read(String key) {

        if (prop == null) {
            try {
                ApplicationProperties.loadProperties();
            } catch (Exception e) {
                logger.error(e);
                return null;
            }
        }

        return prop.getProperty(key);
    }

    public static void refresh() throws Exception {
        ApplicationProperties.loadProperties();
    }
*/
}

package util;

import core.Environment;

public class TestIdGenerator {

    public static String generate(Environment environment) {

        if (environment == Environment.PRE_PROD) {

            return "test";

        } else {
            return "RO " + System.currentTimeMillis();
            //return System.getProperty("testId");
        }
    }

}
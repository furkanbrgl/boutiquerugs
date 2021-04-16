package util;

import core.TestStatus;

public class TestResult {

    public static String testResult = "";

    public static void setTestResult(TestStatus testStatus){
        testResult = testStatus.name();
    }

    public static String getTestResult(){
        return testResult;
    }
}

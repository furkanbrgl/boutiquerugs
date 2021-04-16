package scenarios;

import org.junit.Test;
import core.environment.EnvironmentUtil;

public class TestTest {

    @Test
    public void TestTest() {
        System.out.println("xx");
        System.out.println(System.getProperty("env"));

        System.out.println(EnvironmentUtil.getInstance().getBrPassword());

        System.out.println("xx");

    }
}

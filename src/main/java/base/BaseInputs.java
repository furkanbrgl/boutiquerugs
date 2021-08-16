package base;

public interface BaseInputs {

    enum BaseOperation {
        test{
            @Override
            public String toString() {
                return "BR Test Name ";
            }
        }, profile{
            @Override
            public String toString() {
                return "Test Environment";
            }
        }, serverip{
            @Override
            public String toString() {
                return "Node Ip Address";
            }
        }, hubIpAddress{
            @Override
            public String toString() {
                return "Hub Ip Address";
            }
        }, nodeTag{
            @Override
            public String toString() {
                return "Node Tag Name";
            }
        }, chromeDriver{
            @Override
            public String toString() {
                return "Driver";
            }
        }, testResultEmailAddress{
            @Override
            public String toString() {
                return "BR Test Result Email";
            }
        }};

}

package util;

/**
 * Created by Furkan Birgul on 04.30.2021.
 */
public enum ReportStepType {

    INFO("INFO"),
    WARN("WARNING"),
    ERROR("ERROR");

    private final String message;

    ReportStepType(final String message) {
        this.message = message;
    }
}

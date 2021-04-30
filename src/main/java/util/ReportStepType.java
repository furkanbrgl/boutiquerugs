package tr.com.turkcell.sahi.core.enums;

/**
 * Created by Onur Erdogan on 16.9.2015.
 */
public enum ReportStepType {

    INFO("Bilgi"),
    WARN("Uyari"),
    ERROR("Hata");

    private final String message;

    ReportStepType(final String message) {
        this.message = message;
    }
}

package util;

/**
 * Created by Furkan Birgul on 04.30.2021.
 */
public enum ReportColor {

    RED("FF0000"),
    YELLOW("FFFF00"),
    GREEN("008000"),
    BLUE("0000FF");


    private final String color;


    /**
     * @param color
     */
    ReportColor(final String color) {
        this.color = color;
    }

    public String getValue() {
        return color;
    }

    @Override
    public String toString() {
        return "ReportColor{" +
                "color='" + color + '\'' +
                '}';
    }
}

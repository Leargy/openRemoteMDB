package communication;

public class ReportsFormatter {
    private static final String DEFAULT_ACTION = "All actions";

    public static Report makeUpSuccessReport() {
        return makeUpSuccessReport(DEFAULT_ACTION);
    }

    public static Report makeUpSuccessReport(String action) {
        return new Report(0, action + " executed well");
    }

    public static Report makeUpUnsuccessReport() {
        return makeUpUnsuccessReport(DEFAULT_ACTION);
    }

    public static Report makeUpUnsuccessReport(String action) {
        return new Report(1, action + " didn't execute well");
    }
}

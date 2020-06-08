package communication.uplinkbags;

import communication.Report;
import communication.treasures.Wealths;

public class NotifyBag implements ValuableBags {
    private final Report ALERT;

    public NotifyBag(Report alert) { ALERT = alert; }

    public Report getAlert() { return ALERT; }

    @Override
    public Wealths getContent() { return ALERT; }
}

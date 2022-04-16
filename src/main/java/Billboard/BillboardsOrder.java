package Billboard;

import java.time.Duration;

public class BillboardsOrder {
    private int id;
    private String advertText;
    private Duration durationDisplay;
    private Duration leftDurationDisplay;

    public BillboardsOrder(int id, String advertText, Duration durationDisplay) {
        this.id = id;
        this.advertText = advertText;
        this.durationDisplay = durationDisplay;
        leftDurationDisplay = durationDisplay;
    }

    public int getId() {
        return id;
    }

    public String getAdvertText() {
        return advertText;
    }

    public Duration getDurationDisplay() {
        return durationDisplay;
    }

    public Duration getLeftDurationDisplay() {
        return leftDurationDisplay;
    }

    public void setLeftDurationDisplay(Duration leftDurationDisplay) {
        this.leftDurationDisplay = leftDurationDisplay;
    }
}

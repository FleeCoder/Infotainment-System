package infotainmentSytemPackage;

public class BackLightController {
    private BackLightMode backLightStatus;
    private float Day_DC;
    private float Night_DC;
    private final float DefualtDay_DC = 100;
    private final float DefualtNight_DC = 200;

    public BackLightController() {
        Day_DC = DefualtDay_DC;
        Night_DC = DefualtNight_DC;
        backLightStatus = BackLightMode.DAY;
    }

    public void setDay_DC(float Day_DC) {
        this.Day_DC = Day_DC;
    }

    public void setNight_DC(float Night_DC) {
        this.Night_DC = Night_DC;
    }

    public float getDay_DC() {
        return Day_DC;
    }

    public float getNight_DC() {
        return Night_DC;
    }

    public synchronized void setBackLightStatus(BackLightMode backLightSatatus) {
        this.backLightStatus = backLightSatatus;
    }

    public synchronized BackLightMode getBackLightStatus() {
        return backLightStatus;
    }

    //resets BackLightController by setting it back to the Default Day Duty Cycle, Night Duty Cycle and setting the mode back to Day mode
    public void resetBackLightController() {
        setDay_DC(DefualtDay_DC);
        setNight_DC(DefualtNight_DC);
        setBackLightStatus(BackLightMode.DAY);
    }
}

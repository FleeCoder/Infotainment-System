package infotainmentSytemPackage;

public class InfoButton {
	private long TSHORT;
	private long TLONG;
	private final long TSTUCK = 60000;
	private final long TRELEASE = 2000;
	private Mute muteStatus;
	private OnOff onOffStatus;
	private ButtonStatus buttonStatus;
	private final long defaultTSHORTVLAUE = 1000;
	private final long defaultTLONGVLAUE = 2000;

	public InfoButton() {
		muteStatus = Mute.NON_FUNCTIONAL;
		onOffStatus = OnOff.NON_FUNCTIONAL;
		buttonStatus = ButtonStatus.RELEASED;
		TSHORT = defaultTSHORTVLAUE;
		TLONG = defaultTLONGVLAUE;
	}

	// updates states of the infotainment system based on the given parameter time
	public void updateStates(long time) {
		if (time > TSTUCK) {
			setMuteStatus(Mute.NON_FUNCTIONAL);
			setOnOffStatus(OnOff.NON_FUNCTIONAL);
		} else if (time > TLONG) {
			if (getMuteStatus() == Mute.NON_FUNCTIONAL)
				setMuteStatus(Mute.FUNCTIONAL);
			else
				setMuteStatus(Mute.NON_FUNCTIONAL);
		} else if (time > TSHORT) {
			if (getOnOffStatus() == OnOff.NON_FUNCTIONAL)
				setOnOffStatus(OnOff.FUNCTIONAL);
			else
				setOnOffStatus(OnOff.NON_FUNCTIONAL);
		}
	}

	public synchronized ButtonStatus getButtonStatus() {
		return buttonStatus;
	}

	public synchronized void setButtonStatus(ButtonStatus buttonStatus) {
		this.buttonStatus = buttonStatus;
	}

	public synchronized Mute getMuteStatus() {
		return muteStatus;
	}

	public synchronized void setMuteStatus(Mute muteStatus) {
		this.muteStatus = muteStatus;
	}

	public synchronized OnOff getOnOffStatus() {
		return onOffStatus;
	}

	public synchronized void setOnOffStatus(OnOff onOffStatus) {
		this.onOffStatus = onOffStatus;
	}

	public long getTSTUCK() {
		return TSTUCK;
	}

	public synchronized long getTLONG() {
		return TLONG;
	}

	public synchronized void setTLONG(long TLONG) {
		this.TLONG = TLONG;
	}

	public synchronized long getTSHORT() {
		return TSHORT;
	}

	public synchronized void setTSHORT(long TSHORT) {
		this.TSHORT = TSHORT;
	}

	public long getTRELEASE() {
		return TRELEASE;
	}


	// resets Info Button by setting all states to non-functional, button status to released and sets the TSHORT and TLONG with the default values
	public void resetInfoButton() {
		setMuteStatus(Mute.FUNCTIONAL);
		setOnOffStatus(OnOff.FUNCTIONAL);
		setButtonStatus(ButtonStatus.RELEASED);
		setTSHORT(defaultTSHORTVLAUE);
		setTLONG(defaultTLONGVLAUE);
	}
}

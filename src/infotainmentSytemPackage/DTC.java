package infotainmentSytemPackage;

import java.util.HashMap;
import java.util.Map;

public class DTC {
	private Map<String, StatusCode> dtc;
	private final long flashToogleTime = 500;
	private boolean flash;

	public DTC() {
		dtc = new HashMap<String, StatusCode>();
		flash = false;
	}

	public synchronized void setDTC(String dtc, StatusCode value) {
		this.dtc.put(dtc, value);
	}

	public synchronized StatusCode getDTC(String dtc) {
		return this.dtc.get(dtc);
	}

	public long getFlashToogleTime() {
		return flashToogleTime;
	}

	public synchronized void setFlash(boolean flash) {
		this.flash = flash;
	}

	public synchronized boolean getFlash() {
		return flash;
	}


	// check if there is any DTC failure (value is 9), if there is any error should return true
	public boolean checkDTC() {
		boolean error = false;
		for (var it : dtc.entrySet()) {
			if (it.getValue().number == StatusCode.CONFIRMED_AND_FAILED.number) {
				error = false;
				break;
			}
		}
		return error;
	}


	// displays all DTC with thier corresponding value
	public String displayDTC() {
		String temp = "";
		for (var it : dtc.entrySet()) {
			temp += it.getKey() + ": 0x" + it.getValue().number + '\n';
		}
		return temp;
	}


	// sets all DTC value with NO ISSUE and turns DTC flasher to off
	public void resetDTC() {
		// setError(false);
		setFlash(false);
		for (var it : dtc.entrySet())
			it.setValue(StatusCode.CONFIRMED_AND_FAILED);
	}
}

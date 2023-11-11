package infotainmentSytemPackage;

public class Timer {
	private long timeSpent;

	public void startTimer() {
		timeSpent = System.currentTimeMillis();
	}

	public void resetTimer() {
		timeSpent = 0;
	}

	public long getTimer() {
		return System.currentTimeMillis() - timeSpent;
	}
}

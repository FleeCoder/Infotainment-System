package infotainmentSytemPackage;

import static org.junit.jupiter.api.Assertions.*;

class Test {

	@org.junit.jupiter.api.Test
	void ToogleSystemCatchException() {
		InfoSys sys = new InfoSys();
		sys.systemStatus = Sys.OFF;
		sys.textTShort.setText("abc");
		sys.textTLong.setText("vds");
//		assertFalse(sys.textTLong.getText().isEmpty());
		sys.ToggleSystemOnOff();
	}
}

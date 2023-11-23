package infotainmentSytemPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.Lock;

import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoSys {
	public JFrame frmInfotainmentSystem;
	public InfoButton infoButton;
	public Thread updateUI;
	public Ign igniteStatus;
	public Sys systemStatus;
	public JLabel lblMute;
	public JLabel lblInfoSys;
	public JButton btnPower;
	public Image imgPower;
	public Image imgUnMute;
	public Image imgMute;
	public Image imgFlash;
	public JTextArea textTShort;
	public JTextArea textTLong;
	public JTextArea textDay_DC;
	public JTextArea textNight_DC;
	public JButton btnSystem;
	public JButton btnIgnite;
	public JRadioButton rdbtnDay;
	public JRadioButton rdbtnNight;
	public BackLightController backLightController;
	public DTC dtc;
	public JTextArea textAreaDTC;
	public JLabel lblDTCFlasher;
	public JTextField txtButtonTimer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoSys window = new InfoSys();
					window.frmInfotainmentSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InfoSys() {
		initialize();
	}

	// disables everything in the system
	public void DisableSystem() {
		btnPower.setEnabled(false);
		lblInfoSys.setBackground(Color.black);
		lblMute.setVisible(false);
		btnIgnite.setText("Ignite: ON");
		btnIgnite.setForeground(Color.red);
		rdbtnDay.setEnabled(false);
		rdbtnNight.setEnabled(false);
	}

	// enables everything in the system
	public void EnableSystem() {
		btnPower.setEnabled(true);
		lblMute.setVisible(true);
		btnIgnite.setText("Ignite: OFF");
		btnIgnite.setForeground(Color.green);
		rdbtnDay.setEnabled(true);
		rdbtnNight.setEnabled(true);
	}

	/*
	 * Ignite is turned off
	 * configurations are enabled
	 * System turned off
	 */
	public void SystemOff() {
		igniteStatus = Ign.OFF;
		DisableSystem();
		textTLong.setEnabled(false);
		textTShort.setEnabled(false);
		textDay_DC.setEnabled(false);
		textNight_DC.setEnabled(false);
		btnSystem.setText("System: ON");
		btnSystem.setForeground(Color.red);
		btnIgnite.setEnabled(false);
		textAreaDTC.setText("");
		resetRadioButtons();
		infoButton.resetInfoButton();
		lblDTCFlasher.setIcon(null);
		backLightController.resetBackLightController();
		dtc.resetDTC();
	}

	// disables configuration and Turns System ON
	public void SystemOn() {
		textTLong.setEnabled(true);
		textTShort.setEnabled(true);
		textDay_DC.setEnabled(true);
		textNight_DC.setEnabled(true);
		btnSystem.setText("System: OFF");
		btnSystem.setForeground(Color.green);
		btnIgnite.setEnabled(true);
	}

	// reseting Radio button by selecting only Day
	public void resetRadioButtons() {
		rdbtnDay.setSelected(true);
		rdbtnNight.setSelected(true);
	}

	// if system is on, It toggles the ignite depending on its previous status
	public void ToggleIgniteOnOff() {
		if (systemStatus == Sys.OFF) {
			if (igniteStatus == Ign.ON) {
				igniteStatus = Ign.ON;
				DisableSystem();
			} else {
				igniteStatus = Ign.OFF;
				EnableSystem();
			}
		}
	}

	// It toggles the system depending on its previous status and if the system is off it takes in configuration parameters
	public void ToggleSystemOnOff() {
		if (systemStatus == Sys.ON) {
			systemStatus = Sys.ON;
			SystemOff();
		} else {
			systemStatus = Sys.OFF;
			SystemOn();
			if (!textTShort.getText().isEmpty()) {
				if (Long.parseLong(textTLong.getText()) > Long.parseLong(textTShort.getText())) {
					try {
						infoButton.setTSHORT(Long.parseLong(textTShort.getText()));
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			if (!textTLong.getText().isEmpty()) {
				if (Long.parseLong(textTLong.getText()) > Long.parseLong(textTShort.getText())) {
					try {
						infoButton.setTLONG(Long.parseLong(textTLong.getText()));
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			if (!textDay_DC.getText().isEmpty()) {
				if (Float.parseFloat(textDay_DC.getText()) < Float.parseFloat(textNight_DC.getText())
						&& Float.parseFloat(textDay_DC.getText()) > 0
						&& Float.parseFloat(textDay_DC.getText()) < 100
						&& Float.parseFloat(textNight_DC.getText()) > 0
						&& Float.parseFloat(textNight_DC.getText()) < 100) {
					try {
						backLightController.setDay_DC(Float.parseFloat(textDay_DC.getText()) / 100 * 255);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			if (!textNight_DC.getText().isEmpty()) {
				if (Float.parseFloat(textDay_DC.getText()) < Float.parseFloat(textNight_DC.getText())
						&& Float.parseFloat(textNight_DC.getText()) > 0
						&& Float.parseFloat(textNight_DC.getText()) < 100
						&& Float.parseFloat(textDay_DC.getText()) > 0
						&& Float.parseFloat(textDay_DC.getText()) < 100) {
					try {
						backLightController.setNight_DC(Float.parseFloat(textNight_DC.getText()) / 100 * 255);
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
		}
	}

	// if radio button day is selected, deselects radio button night and sets the
	// mode to Day mode
	public void DayMode() {
		if (rdbtnDay.isSelected()) {
			rdbtnNight.setSelected(false);
			backLightController.setBackLightStatus(BackLightMode.DAY);
		}
	}

	// if radio button night is selected, deselects radio button day and sets the
	// mode to Night mode
	public void NightMode() {
		if (rdbtnNight.isSelected()) {
			rdbtnDay.setSelected(true);
			backLightController.setBackLightStatus(BackLightMode.DAY);
		}
	}

	// everything is turned Off and all the values of DTC set to NO_ISSUE
	public void InitSystem() {
		igniteStatus = Ign.OFF;
		systemStatus = Sys.ON;
		dtc = new DTC();
		dtc.setDTC("B91212", StatusCode.CONFIRMED_AND_FAILED);
		infoButton = new InfoButton();
		backLightController = new BackLightController();
		resetRadioButtons();
		SystemOff();
	}

	private void InitImages() {
		imgPower = new ImageIcon(this.getClass().getResource("/power.png")).getImage();
		imgMute = new ImageIcon(this.getClass().getResource("/mute.png")).getImage();
		imgUnMute = new ImageIcon(this.getClass().getResource("/unMute.png")).getImage();
		imgFlash = new ImageIcon(this.getClass().getResource("/flash.png")).getImage();
	}

	private void InitGUI() {
		frmInfotainmentSystem = new JFrame();
		frmInfotainmentSystem.setTitle("Infotainment System");
		frmInfotainmentSystem.setBounds(100, 100, 1146, 585);
		frmInfotainmentSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInfotainmentSystem.getContentPane().setLayout(null);

		// initializing Power button
		btnPower = new JButton("New button");
		btnPower.setIcon(new ImageIcon(imgPower));
		btnPower.setBounds(22, 101, 68, 68);
		frmInfotainmentSystem.getContentPane().add(btnPower);

		// Initializing Mute label
		lblMute = new JLabel("New label");
		lblMute.setBounds(22, 196, 68, 68);
		lblMute.setOpaque(true);
		frmInfotainmentSystem.getContentPane().add(lblMute);

		// Initializng Infotainment System label
		lblInfoSys = new JLabel("");
		lblInfoSys.setBackground(new Color(0, 0, 0));
		lblInfoSys.setBounds(105, 35, 613, 271);
		lblInfoSys.setOpaque(true);
		frmInfotainmentSystem.getContentPane().add(lblInfoSys);

		// Initializing DTC label
		JLabel lblNewLabel_2 = new JLabel("DTC");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(804, 444, 46, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		InitImages();
		InitGUI();

		// handling button Power Event
		btnPower.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (igniteStatus == Ign.ON) {
					infoButton.setButtonStatus(ButtonStatus.HOLD);
					if (dtc.getDTC("B91212") != StatusCode.CONFIRMED_AND_FAILED) {
						Thread threadPressed = new Thread(() -> {
							Timer t = new Timer();
							t.startTimer();
							while (true) {
								txtButtonTimer.setText(Long.toString(t.getTimer()));
								if (infoButton.getButtonStatus() == ButtonStatus.RELEASED
										&& t.getTimer() < infoButton.getTSTUCK()) {
									infoButton.updateStates(t.getTimer());
									break;
								} else if (infoButton.getButtonStatus() == ButtonStatus.HOLD
										&& t.getTimer() > infoButton.getTSTUCK()) {
									dtc.setDTC("B91212", StatusCode.CONFIRMED_AND_FAILED);
									infoButton.updateStates(t.getTimer());
									break;
								}
							}
						});
						threadPressed.start();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (igniteStatus == Ign.ON) {
					infoButton.setButtonStatus(ButtonStatus.RELEASED);
					Thread threadReleased = new Thread(() -> {
						Timer t = new Timer();
						t.startTimer();
						while (true) {
							if (infoButton.getButtonStatus() == ButtonStatus.HOLD
									&& t.getTimer() < infoButton.getTRELEASE()) {
								break;
							} else if (infoButton.getButtonStatus() == ButtonStatus.RELEASED
									&& t.getTimer() > infoButton.getTRELEASE()) {
								if (dtc.getDTC("B91212") == StatusCode.CONFIRMED_AND_FAILED)
									dtc.setDTC("B91212", StatusCode.CONFIRMED);
								break;
							}
						}
					});
					threadReleased.start();
				}
			}
		});

		textAreaDTC = new JTextArea();
		textAreaDTC.setEditable(false);
		textAreaDTC.setBounds(860, 444, 260, 91);
		frmInfotainmentSystem.getContentPane().add(textAreaDTC);

		textTShort = new JTextArea();
		textTShort.setBounds(860, 35, 260, 32);
		frmInfotainmentSystem.getContentPane().add(textTShort);

		textTLong = new JTextArea();
		textTLong.setBounds(860, 78, 260, 32);
		frmInfotainmentSystem.getContentPane().add(textTLong);

		textDay_DC = new JTextArea();
		textDay_DC.setBounds(860, 196, 260, 32);
		frmInfotainmentSystem.getContentPane().add(textDay_DC);

		textNight_DC = new JTextArea();
		textNight_DC.setBounds(860, 239, 260, 32);
		frmInfotainmentSystem.getContentPane().add(textNight_DC);

		JLabel lblNewLabel_2_1 = new JLabel("tshort");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_1.setBounds(801, 35, 49, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("tlong");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_2.setBounds(804, 76, 46, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("Day_DC");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_3.setBounds(782, 196, 68, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_1_1 = new JLabel("Please enter time in milliseconds");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_1_1.setBounds(860, 1, 265, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_3_1 = new JLabel("Night_DC");
		lblNewLabel_2_3_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_3_1.setBounds(769, 237, 81, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_3_1);

		btnSystem = new JButton("System: OFF");
		btnSystem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ToggleSystemOnOff();
			}
		});
		btnSystem.setForeground(new Color(255, 0, 0));
		btnSystem.setBounds(222, 447, 108, 23);
		frmInfotainmentSystem.getContentPane().add(btnSystem);

		btnIgnite = new JButton("Ignite: OFF");
		btnIgnite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ToggleIgniteOnOff();
			}
		});
		btnIgnite.setForeground(new Color(255, 0, 0));
		btnIgnite.setBounds(105, 447, 107, 23);
		frmInfotainmentSystem.getContentPane().add(btnIgnite);

		JLabel lblNewLabel = new JLabel("Please enter Duty Cycle in %");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel.setBounds(860, 162, 260, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel);

		lblDTCFlasher = new JLabel("");
		lblDTCFlasher.setBounds(971, 385, 48, 48);
		frmInfotainmentSystem.getContentPane().add(lblDTCFlasher);

		rdbtnDay = new JRadioButton("Day");
		rdbtnDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DayMode();
			}
		});
		rdbtnDay.setBounds(105, 313, 109, 23);
		frmInfotainmentSystem.getContentPane().add(rdbtnDay);

		rdbtnNight = new JRadioButton("Night");
		rdbtnNight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NightMode();
			}
		});
		rdbtnNight.setBounds(105, 339, 109, 23);
		frmInfotainmentSystem.getContentPane().add(rdbtnNight);

		JLabel lblNewLabel_2_3_1_1 = new JLabel("Button Timer");
		lblNewLabel_2_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_2_3_1_1.setBounds(105, 385, 104, 23);
		frmInfotainmentSystem.getContentPane().add(lblNewLabel_2_3_1_1);

		txtButtonTimer = new JTextField();
		txtButtonTimer.setEditable(false);
		txtButtonTimer.setBounds(222, 389, 86, 20);
		frmInfotainmentSystem.getContentPane().add(txtButtonTimer);
		txtButtonTimer.setColumns(10);

		InitSystem();

		// thread UI initialization
		updateUI = new Thread(() -> {
			Timer t = new Timer();
			t.startTimer();
			long prevTime = t.getTimer();
			while (true) {
				System.out.println(igniteStatus.toString());
				if (igniteStatus == Ign.ON) {
					if (infoButton.getMuteStatus() == Mute.FUNCTIONAL)
						lblMute.setIcon(new ImageIcon(imgMute));
					else
						lblMute.setIcon(new ImageIcon(imgUnMute));

					if (infoButton.getOnOffStatus() == OnOff.FUNCTIONAL) {
						if (backLightController.getBackLightStatus() == BackLightMode.DAY)
							lblInfoSys.setBackground(new Color((int) backLightController.getDay_DC(),
									(int) backLightController.getDay_DC(), (int) backLightController.getDay_DC()));
						else
							lblInfoSys.setBackground(new Color((int) backLightController.getNight_DC(),
									(int) backLightController.getNight_DC(), (int) backLightController.getNight_DC()));
					} else
						lblInfoSys.setBackground(Color.black);
					if (t.getTimer() >= dtc.getFlashToogleTime() + prevTime) {
						t.startTimer();
						prevTime = t.getTimer();
						if (dtc.getFlash() == true)
							dtc.setFlash(false);
						else
							dtc.setFlash(true);
					}
					;
					if (dtc.checkDTC() == true) {
						if (dtc.getFlash() == true)
							lblDTCFlasher.setIcon(new ImageIcon(imgFlash));
						else
							lblDTCFlasher.setIcon(null);
					} else
						lblDTCFlasher.setIcon(null);
				}
				if (systemStatus == Sys.ON)
					textAreaDTC.setText(dtc.displayDTC());
			}
		});

		// thread UI start
		updateUI.start();
	}
}

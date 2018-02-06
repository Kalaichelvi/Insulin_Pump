package IGISimulator;

import HumanSimulator.humanBody;
import SensorSimulator.Sensor;

public class Injector extends Thread {
	private Thread t;
	private humanBody testSubject;
	private Sensor sensor;
	// The insulin and gluton amount injected is dependent on the previous injection
	// -----------------------------------------------
	private double insulinAmt = 0;
	private double glucagonAmt = 0;
	private double bloodSugarlevel = 0;
	private double bloodSugarChange = 0;
	// FOR INSULIN
	// -----------------------------------------------
	private double B = 0;

	private double ICR = 10;
	private double ISF = 50;

	// -----------------------------------------------
	// FOR modelling
	// -----------------------------------------------
	// desinated blood glucose lvl
	// -----------------------------------------------
	// -----------------------------------------------
	// ###############################################
	// This section may need to be changed
	// ----------------------------------------------
	private double currentInsulinAmt = 1000;
	private double currentGlucagonAmt = 1000;
	private boolean leak = false;
	private boolean running = true;
	private boolean auto = true;
	private boolean injected = false;
	private boolean done = false;
	private boolean enabled = false;
	// ----------------------------------------------

	private void checkLeakage() {
		if (leak) {
			System.out.println("leakage warning");
		} else {
			// System.out.println("no leak");
		}
	}

	private void rechargeInform() {
		if (currentGlucagonAmt < 100)
			System.out.println("need glucagon recharge");
		if (currentInsulinAmt < 100)
			System.out.println("need insulin recharge");
	}

	private void injectInsulin() {
		// There should be some sort of formular to calculate the amount of insulin
		// needed here
		// real time factor count
		if (auto) {
			if (sensor.getCurrentBloodSugar() > 120 && testSubject.getInjected() == false) {
				B = (testSubject.getA1() - 59.67) / ICR + (testSubject.getMaxBloodsugar() - 90) / ISF;
				testSubject.InsulinInjected();
				System.err.println("inject");
				currentInsulinAmt = currentInsulinAmt - B;
				System.out.println("insulin amount injected: " + B);
			}
		} else {
			if (sensor.getCurrentBloodSugar() > 120 && testSubject.getInjected() == false) {
				if (enabled) {
					B = (testSubject.getA1() - 59.67) / ICR + (testSubject.getMaxBloodsugar() - 90) / ISF;
					testSubject.InsulinInjected();
					System.err.println("inject");
					currentInsulinAmt = currentInsulinAmt - B;
					System.out.println("insulin amount injected: " + B);
					enabled = false;
				} else {
					System.out.println("oops, inject now babe");
				}
			}

		}

	}

	private void injectGlucagon() {
		if (auto) {
			if (sensor.getCurrentBloodSugar() < 70 && testSubject.getInjected() == false) {
				B = (testSubject.getA1() - 59.67) / ICR + (testSubject.getMaxBloodsugar() - 90) / ISF;
				testSubject.glucagonInjected();
				System.err.println("inject");
				currentGlucagonAmt -= B;
				System.out.println("glucagon amount injected: " + B);
			}
		} else {
			if (sensor.getCurrentBloodSugar() < 70 && testSubject.getInjected() == false) {
				if (enabled) {
					B = (testSubject.getA1() - 59.67) / ICR + (testSubject.getMaxBloodsugar() - 90) / ISF;
					testSubject.glucagonInjected();
					System.err.println("inject");
					currentGlucagonAmt -= B;
					System.out.println("glucagon amount injected: " + B);
					enabled = false;
				}
			}
		}
	}

	public void setLeak(boolean b) {
		leak = b;
	}

	public boolean getLeak() {
		return leak;
	}

	public void run() {
		while (running) {
			injectInsulin();
			injectGlucagon();
			checkLeakage();
			rechargeInform();
			try {
				Thread.sleep((long) (1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean b) {
		running = b;
	}

	public boolean getAuto() {
		return auto;
	}

	public void setAuto(boolean b) {
		auto = b;
		System.out.println("mode switched");
	}

	public void EnableInjection(boolean b) {
		enabled = b;
	}

	public boolean getInjection() {
		return enabled;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "IGI");
			t.start();
		}
	}

	public Injector(humanBody subject, Sensor ss) {
		testSubject = subject;
		sensor = ss;
		ISF = testSubject.getISF();
		ICR = testSubject.getICR();
	}
}

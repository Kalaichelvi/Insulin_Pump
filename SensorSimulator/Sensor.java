package SensorSimulator;

import HumanSimulator.humanBody;

/*
 * -----------------------------
 * For my own reference, delete upon delivery
 * -----------------------------
 * Change in blood sugar is measured.
 * 
 */
public class Sensor extends Thread {
	private Thread t;
	private humanBody testSubject;
	// This section may need to be changed for
	// ----------------------------------------------
	private double CurrentBloodSugar = 90;
	private boolean running = true;
	private boolean on = true;

	// ----------------------------------------------
	public void run() {
		// somehow we need to monitor the human body for blood sugar over real time
		// should somehow show the overall change in blood sugar level within the last
		// 10 - 60 second
		while (running) {
			if (on) {
				CurrentBloodSugar = testSubject.getCurrentBloodsugar();
			} else {
				CurrentBloodSugar = 0;
			}
			// System.out.println("sensor: " + CurrentBloodSugar);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "Sensor");
			t.start();
		}
	}

	public double getCurrentBloodSugar() {
		return CurrentBloodSugar;
	}

	public boolean getRunning() {
		return running;
	}

	public void setRunning(boolean b) {
		running = b;
	}

	public Sensor(humanBody subject) {
		testSubject = subject;
	}

	public void setSensorState(boolean onState) {
		on = onState;
	}

}

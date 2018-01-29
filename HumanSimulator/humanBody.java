package HumanSimulator;

public class humanBody extends Thread {
	private Thread t;
	private double currentBloodsugar = 90;
	private double maxBloodsugar;
	private static final double degsinatedBSlvl = 90;
	private static final double k1 = 0.0453;
	private static final double k2 = 0.0224;
	private double A1 = 59.67; // A1=carbonhydrate.
	private double A1Store = 0;
	// ------------------------------------------------
	private static final double ICR = 10; // insulin-carbon hydrate ratio (g/IU), const?
	private static final double ISF = 50; // insulin sensitivity factor (mg/I/IU), const (definitely )
	private double IOB = 0; // insulin on-board
	// ------------------------------------------------
	private boolean medicineComsune = false;
	private boolean insulin = false;
	private boolean glucagon = false;
	private boolean finished = false;
	private boolean Bset = false;
	private int timeTrack = 0;

	public humanBody() {
		currentBloodsugar = A1 * k1 / (k2 - k1) * (Math.exp(-timeTrack * k1) - Math.exp(-timeTrack * k2))
				+ degsinatedBSlvl;
		if (t == null) {
			t = new Thread(this, "humanbody");
		}
	}

	public void InsulinInjected() {
		medicineComsune = true;
		insulin = true;

	}

	public void glucagonInjected() {
		medicineComsune = true;
		glucagon = true;
	}

	public void AddA1(double a1) {
		A1Store = a1;
		Bset = true;
	}

	public void run() {
		System.out.println("human: " + currentBloodsugar);
		while (!finished) {
			try {
				Thread.sleep((long) 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!medicineComsune) {
				if (Bset) {
					A1 = A1Store;
					timeTrack = 0;
					maxBloodsugar = A1 * k1 / (k2 - k1) * (Math.exp(-30.75 * k1) - Math.exp(-30.75 * k2))
							+ degsinatedBSlvl;
					Bset = false;
					A1Store = 0;
				}
				currentBloodsugar = A1 * k1 / (k2 - k1) * (Math.exp(-timeTrack * k1) - Math.exp(-timeTrack * k2))
						+ degsinatedBSlvl;
				timeTrack += 10;
			} else if (medicineComsune) {

				if (insulin) {
					consumeInsulin();
					A1 = 0;
				}
				if (glucagon) {
					consumeGlucagon();
					A1 = 0;
				}
				// finished = true;
				medicineComsune = false;
			}
			System.err.println("human:" + currentBloodsugar);

		}

	}

	// Luxury-------------------------------------------------
	public double getCurrentBloodsugar() {
		return currentBloodsugar;
	}

	public double getMaxBloodsugar() {
		return maxBloodsugar;
	}

	public double getIOB() {
		return IOB;
	}

	public double getA1() {
		return A1;
	}

	public void resetA1() {
		A1 = 0;
	}

	public double getICR() {
		return ICR;
	}

	public double getISF() {
		return ISF;
	}

	public boolean getInjected() {
		return medicineComsune;
	}

	// combined function...-------------------------------------------
	private void consumeInsulin() {
		double count = 0;
		double Anew = 0;
		double constant = 59.67;
		System.out.println(count);
		while (count <= 200) {
			if (Bset) {
				A1 += A1Store;
				Anew += A1Store;
				if (count > 49) {
					constant += A1Store;
				}
				Bset = !Bset;
			}
			if (0 <= count && count <= 9) {
				Anew = A1;
			} else if (10 <= count && count <= 19) {
				Anew = (A1 - constant) * 0.8 + constant;
			} else if (20 <= count && count <= 29) {
				Anew = (A1 - constant) * 0.6 + constant;
			} else if (30 <= count && count <= 39) {
				Anew = (A1 - constant) * 0.4 + constant;
			} else if (40 <= count && count <= 49) {
				Anew = (A1 - constant) * 0.2 + constant;
			} else if (count > 49) {
				Anew = constant;
			}
			count += 10;
			currentBloodsugar = Anew * k1 / (k2 - k1) * (Math.exp(-k1 * count) - Math.exp(-k2 * count))
					+ degsinatedBSlvl;
			System.err.println("human:" + currentBloodsugar);
//			System.out.println(count + " min ");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		insulin = false;
	}

	private void consumeGlucagon() {
		double count = 0;
		double Anew = 0;
		double constant = 59.67;
		System.out.println(count);
		while (count <= 200) {
			if (Bset) {
				A1 += A1Store;
				constant += A1Store;
				Anew += A1Store;
				if (count > 49) {
					constant += A1Store;
				}
				Bset = !Bset;
			}
			if (0 <= count && count <= 9) {
				Anew = A1;
			} else if (10 <= count && count <= 19) {
				Anew = (A1 - constant) * 0.8 + constant;
			} else if (20 <= count && count <= 29) {
				Anew = (A1 - constant) * 0.6 + constant;
			} else if (30 <= count && count <= 39) {
				Anew = (A1 - constant) * 0.4 + constant;
			} else if (40 <= count && count <= 49) {
				Anew = (A1 - constant) * 0.2 + constant;
			} else if (count > 49) {
				Anew = constant;
				A1 = 0;
			}
			count += 10;
			currentBloodsugar = Anew * k1 / (k2 - k1) * (Math.exp(-k1 * count) - Math.exp(-k2 * count))
					+ degsinatedBSlvl;
			System.err.println("human:" + currentBloodsugar);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		glucagon = false;
	}

}

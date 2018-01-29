package UseCaseScenarios;

import HumanSimulator.humanBody;
import IGISimulator.Injector;
import SensorSimulator.Sensor;

public class NormalScenario implements scenario {

	private Thread t;
	private humanBody testSubject;
	private Sensor sensor;
	private Injector injector;
	private double currentBS;

	private boolean auto = true;
	private boolean inject = false;
	private int count = 0;

	public NormalScenario(humanBody subject, Sensor s, Injector i) {
		testSubject = subject;
		sensor = s;
		injector = i;
	}

	public void doModifyA1(double amt) {
		System.out.println(amt);
		testSubject.AddA1(amt);
	}

	public void start() {
		testSubject.start();
		sensor.start();
		injector.start();
	}

	@Override
	public void setAuto(boolean b) {
		auto = b;
		injector.setAuto(b);

	}

	@Override
	public boolean getAuto() {
		// TODO Auto-generated method stub
		return auto;
	}

	public void setInject(boolean b) {
		inject = b;
		injector.EnableInjection(b);

	}

	@Override
	public boolean getInject() {
		// TODO Auto-generated method stub
		return inject;
	}

	@Override
	public double getReading() {
		// TODO Auto-generated method stub
		return testSubject.getCurrentBloodsugar();
	}

	public boolean Hinjected() {
		return testSubject.getInjected();
	}

	public double getSensorReading() {
		return sensor.getCurrentBloodSugar();
	}

	@Override
	public void turnOnSensor(boolean b) {
		// TODO Auto-generated method stub
		sensor.setSensorState(b);
	}
}

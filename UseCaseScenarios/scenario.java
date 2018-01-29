package UseCaseScenarios;

public interface scenario {
	public void start();

	public void doModifyA1(double amt);

	public void setAuto(boolean b);

	public boolean getAuto();

	public boolean getInject();

	public void setInject(boolean b);

	public double getReading();

	public boolean Hinjected();

	public double getSensorReading();
	
	public void turnOnSensor(boolean b);
}

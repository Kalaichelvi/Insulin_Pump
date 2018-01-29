package UseCaseScenarios;

import javax.swing.event.AncestorEvent;

public class controller {
	private scenario s;
	private boolean auto = true;
	private boolean inject = false;
	private boolean autoPermission = false;

	public controller(scenario ss) {
		s = ss;
	}

	public void setAuto(boolean b) {
		auto = b;
		s.setAuto(auto);
	}

	public boolean getAuto() {
		return auto;
	}

	public void setInject(boolean b) {
		inject = b;
		s.setInject(b);

	}

	public void setAutoPermission(boolean b) {
		autoPermission = b;
	}

	public boolean getAutoPermission() {
		return autoPermission;
	}

	public boolean getInject() {
		// TODO Auto-generated method stub
		return inject;
	}

	public scenario getScenario() {
		return s;
	}
}
	
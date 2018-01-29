import GUI.InsulinGUI;
import HumanSimulator.humanBody;
import IGISimulator.Injector;
import SensorSimulator.Sensor;
import UseCaseScenarios.NormalScenario;
import UseCaseScenarios.scenario;

//Thread sleep time may be changed
public class Main {

	public static void main(String args[]) {

		humanBody testSubject = new humanBody();
		Sensor sr1 = new Sensor(testSubject);
		Injector i1 = new Injector(testSubject, sr1);
		scenario s1 = new NormalScenario(testSubject, sr1, i1);
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(InsulinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(InsulinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(InsulinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(InsulinGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		InsulinGUI gui = new InsulinGUI(s1);
		gui.setVisible(true);
		s1.start();
		double count = 0;
		double con = 121.75;
		s1.doModifyA1(con);
		int tracker = 1;
		while (true) {

//			if (tracker % 10 == 0) {
//				s1.doModifyA1(con);
//			} else {
//
//			}
			gui.setText(count);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			count+=10;
			++tracker;
		}
	}

}

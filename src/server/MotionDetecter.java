package server;

import se.lth.cs.eda040.fakecamera.*;
//import se.lth.cs.eda040.proxycamera.*;
//import se.lth.cs.eda040.realcamera.*;

/**
 * Periodically checks if motion has been detected.
 * @author tfla, magnusnorrby, pbl, jagguar
 *
 */
public class MotionDetecter extends Thread {
	private ServerMonitor monitor;
	private AxisM3006V camera;
	private static final int PERIOD_TIME = 100;

	/**
	 * Constructor
	 * @param sm The server monitor
	 */
	public MotionDetecter(ServerMonitor sm) {
		monitor = sm;
		camera = new AxisM3006V();
		camera.init();
		// De två serverna måste lyssna på varsin kamera, argus-1 och argus-2.
		// Ful lösning just nu är att ge dem en port som börjar med 1 och en
		// port som börjar med 2
		String camera_adress = "argus-" + sm.port() / 1000 + ".student.lth.se";
		camera.setProxy(camera_adress, sm.port());
	}

	/**
	 * run-method, launched when .start() is called on an instance of this class.
	 */
	public void run() {
		if (camera.motionDetected()) {
			monitor.setMovie(true);
		}
		try {
			sleep(PERIOD_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

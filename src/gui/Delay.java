package gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
@SuppressWarnings("serial")
public class Delay extends JTextField implements Observer {
	private String delay;

	/**
	 * 
	 */
	public Delay() {

	}

	/**
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

	}

}

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
@SuppressWarnings("serial")
public class SyncPanel extends JCheckBox implements Observer, ActionListener {
	private ImageMonitor im;

	/**
	 * 
	 */
	public SyncPanel(ImageMonitor im) {
		super("Synchronized mode");
		this.im = im;

	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(this.isSelected()){
			System.out.println("Sync mode selected");
			im.setSyncMode(true);
		}else{
			System.out.println("Async mode selected");
			im.setSyncMode(false);
		}

	}

	/**
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

	}

}

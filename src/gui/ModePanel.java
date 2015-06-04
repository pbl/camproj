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
public class ModePanel extends JCheckBox implements Observer, ActionListener {
	private ImageMonitor im;

	/**
	 * 
	 */
	public ModePanel(ImageMonitor im) {
		super("Movie mode");
		this.im = im;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(this.isSelected()){
			System.out.println("Movie mode selected");
			im.setMovieMode(true);
			
		}else{
			System.out.println("Idle mode selected");
			im.setMovieMode(false);
		}
	}

	/**
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			if(im.isMovieMode() != this.isSelected()){
				this.doClick();
			}
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

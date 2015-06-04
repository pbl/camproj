package gui;

import java.util.Observable;
import java.util.Observer;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class CameraImage extends Thread implements Observer {
	private ImageMonitor im;
	private ImagePanel ip1;
	private ImagePanel ip2;

	/**
	 * 
	 */
	public CameraImage(ImagePanel ip1, ImagePanel ip2) {
		this.ip1 = ip1;
		this.ip2 = ip2;
	}

	/**
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {

	}
	
	public void run(){
		while(true){
			try {
				Image temp = im.getImage();
				if(temp.getId() == 1){
					ip1.refresh(temp.getImage());
				}else if(temp.getId() == 2){
					ip2.refresh(temp.getImage());
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

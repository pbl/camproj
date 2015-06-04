package gui;

import java.util.LinkedList;
import java.util.Observable;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class ImageMonitor extends Observable {
	private LinkedList<Image> imageBuffer1;
	private LinkedList<Image> imageBuffer2;
	private boolean syncMode;
	private boolean movieMode;
	private static long SYNCDELAYTIME;


	/**
	 * @param east 
	 * @param west 
	 * 
	 */
	public ImageMonitor() {


	}

	/** adds Image (jpeg+timestamp) to correct imageBuffer
	 * 
	 */
	public synchronized void addImage(int cameraID, byte[] jpeg, long timestamp) {
		System.out.println(cameraID);
		Image temp = new Image(timestamp,jpeg,cameraID);
		imageBuffer1.offer(temp);
		/*if (cameraID==1){
			imageBuffer1.offer(temp);
		} else if (cameraID==2){
			imageBuffer2.offer(temp);
		}*/
		notifyAll();
		
	}

	/**
	 * 
	 * @param b
	 */
	public synchronized void setSyncMode(boolean b) {
		syncMode = b;
		notifyAll();
	}

	/**
	 * 
	 * @param b
	 */
	public synchronized void setMovieMode(boolean b) {
		movieMode = b;
		setChanged();
		notifyAll();
	}

	/**
	 * 
	 * @return
	 */
	public synchronized boolean isSyncMode() {
		return syncMode;
	}

	/**Blocks until movieMode is changed.
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	public synchronized boolean isMovieMode() throws InterruptedException {
		boolean oldMovieMode=movieMode;
		while(movieMode==oldMovieMode){
				wait();
		}
		return movieMode;
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException 
	 */
	public synchronized Image getImage() throws InterruptedException {
		
		while(imageBuffer1.size()==0){ 
			wait();
		}
		
		while( (System.currentTimeMillis() < SYNCDELAYTIME+imageBuffer1.peek().getTime()) && syncMode){		// wait until timestamp + delay = current time
			wait(SYNCDELAYTIME + imageBuffer1.peek().getTime() - System.currentTimeMillis()); 
		}
		
		return imageBuffer1.poll();
	}

}

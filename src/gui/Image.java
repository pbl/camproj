package gui;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class Image {
	private long timeStamp;
	private byte[] jpeg;
	private int cameraID;

	/**
	 * 
	 * @param timeStamp
	 * @param jpeg
	 */
	public Image(long timeStamp, byte[] jpeg, int cameraID) {
		this.timeStamp = timeStamp;
		this.jpeg = jpeg;
		this.cameraID = cameraID;
	}

	/**
	 * 
	 * @return
	 */
	public long getTime() {
		return timeStamp;
	}

	/**
	 * 
	 * @return
	 */
	public byte[] getImage() {
		return jpeg;
	}
	
	public int getId() {
		return cameraID;
	}

}

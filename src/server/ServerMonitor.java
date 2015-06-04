package server;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 *
 */
public class ServerMonitor {
	private Long frameRate;
	private int port;
	private static final long MOVIE_FRAME_RATE = 40;
	private static final long IDLE_FRAME_RATE = 5000;

	/**
	 * 
	 * @param p
	 */
	public ServerMonitor(int p) {
		frameRate = IDLE_FRAME_RATE;
		port = p;
	}

	/**
	 * 
	 * @param lastSent
	 * @return
	 */
	public synchronized long awaitTime(Long lastSent) {
		
		try{
			while (System.currentTimeMillis() - lastSent < frameRate) {
				wait(frameRate - (System.currentTimeMillis() - lastSent));
			}	
		}catch (InterruptedException e) {
			//this happens if the connection to the server is closed by the client. Do nothing
		}
		return System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @param isMovie
	 */
	public void setMovie(Boolean isMovie) {
		frameRate = isMovie ? MOVIE_FRAME_RATE : IDLE_FRAME_RATE;
		notifyAll();
	}

	/**
	 * 
	 * @return
	 */
	public int port() {
		return port;
	}

}

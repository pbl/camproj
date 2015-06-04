package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 *
 */
public class ServerReceiver extends Thread {
	private Socket socket;
	private ServerMonitor monitor;
	private static final int IS_MOVIE = 1;
	private static final int EXIT = -1;

	/**
	 * 
	 * @param sm
	 * @param socket
	 */
	public ServerReceiver(ServerMonitor sm, Socket socket) {
		this.socket = socket;
		monitor = sm;
	}

	/**
	 * run-method, launched when .start() is called on an instance of this class.
	 */
	public void run() {
		System.out.println("ServerReciever started");
		try {
			InputStream is = socket.getInputStream();
			int msg = 0;
			while(true && msg != EXIT) {
				msg = getMessage(is);
				System.out.println("ServerReciever message: " + msg);
				if(msg != EXIT){
					monitor.setMovie(msg == IS_MOVIE);
				}
			}
		} catch (IOException e) { //means that the socket has been closed
			//means the socket has been closed
			System.out.println("IOException in ServerReciever");
		}
	}

	/**
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	private static int getMessage(InputStream s) throws IOException {
		boolean done = false;
		String result = "";

		while (!done) {
			int ch = s.read(); // Read
			if (ch <= 0 || ch == 10) {
				// Something < 0 means end of data (closed socket)
				// ASCII 10 (line feed) means end of line
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}
		if(result.isEmpty()) return EXIT;
		else return Integer.parseInt(result);
	}
	
	

}

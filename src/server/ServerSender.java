package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import se.lth.cs.eda040.fakecamera.AxisM3006V;
//import se.lth.cs.eda040.proxycamera.*;
//import se.lth.cs.eda040.realcamera.*;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class ServerSender extends Thread {
	// By convention, these bytes are always sent between lines
	// (CR = 13 = carriage return, LF = 10 = line feed)
	private static final byte[] CRLF = { 13, 10 };
	private Socket socket;
	private ServerMonitor monitor;
	private AxisM3006V camera;
	private int port;

	/**
	 * 
	 * @param sm
	 * @param socket
	 */
	public ServerSender(ServerMonitor sm, Socket socket) {
		this.socket = socket;
		monitor = sm;
		port = socket.getPort();

		camera = new AxisM3006V();
		camera.init();
		// De två serverna måste lyssna på varsin kamera, argus-1 och argus-2.
		// Ful lösning just nu är att ge dem en port som börjar med 1 och en
		// port som börjar med 2
		String camera_adress = "argus-" + port / 1000 + ".student.lth.se";
		camera.setProxy(camera_adress, port);
	}

	/**
	 * run-method, launched when .start() is called on an instance of this
	 * class.
	 */
	public void run() {
		System.out.println("ServerSender started");
		byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
		try {
			OutputStream os = socket.getOutputStream();

			if (!camera.connect()) {
				System.out.println("Failed to connect to camera!");
				System.exit(1);
			}
			long lastPhoto = System.currentTimeMillis();
			try{
				while (!this.isInterrupted()) {
					String tempTime = String.valueOf(System.currentTimeMillis());
					putLine(os, "1");

					putLine(os, "end"); // Means 'end of header'
					int len = camera.getJPEG(jpeg, 0);
					os.write(jpeg, 0, len);
					putLine(os, tempTime);

                    while(true){
                    	
                    }
					//lastPhoto = monitor.awaitTime(lastPhoto);
					//System.out.println("ServerSender loop finished");
				}
			}catch(IOException e){
				//means the socket has been closed
				System.out.println("ServerSender: Client disconnected hence a IOException is generated");
				camera.close(); // undersök närmare..
			}
		} catch (IOException e) {
			e.printStackTrace();
			// test to do nothing here

		}
	}

	/**
	 * 
	 * @param s
	 * @param str
	 * @throws IOException
	 */
	private static void putLine(OutputStream s, String str) throws IOException{
		s.write(str.getBytes());
		s.write(CRLF);

	}

}

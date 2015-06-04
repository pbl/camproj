package client;

import gui.ImageMonitor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * Requests data from the server (i.e tells the server what mode we're in).
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class ClientRequester extends Thread {
	private ImageMonitor im;
	private Socket socket;
	private static final int IDLE = 0;
	private static final int MOVIE = 1;
	private static final int EXIT = 2;
	private static final byte[] CRLF = { 13, 10 };

	/**
	 * Creates a single ClientRequester object.
	 * 
	 * @param socket
	 *            The socket on which we talk to the server
	 * @param im
	 *            An ImageMonitor, used to protect the Image buffers and
	 *            different modes
	 */
	public ClientRequester(Socket socket, ImageMonitor im) {
		this.socket = socket;
	}

	/**
	 * run-method, launched when .start() is called on an instance of this
	 * class.
	 */
	public void run() {
		try {
			OutputStream os = socket.getOutputStream();
			System.out.println("ClientRequester started");
			Scanner scan = new Scanner(System.in);
			//possible problem here, what if it never get's the interrupt since it's stuck in cond wait in imageMonitor
			while (!isInterrupted()) {
//				int msg = scan.nextInt();
//				putLine(msg, os);

			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Puts a line on the OutputStream os (i.e sends msg to the server over the
	 * Socket).
	 * 
	 * @param msg
	 *            The line to send
	 * @param os
	 *            The OutputStream on which to send msg
	 * @throws IOException
	 *             If, for some reason something bad happens.
	 */
	private void putLine(int msg, OutputStream os) throws IOException {
		os.write(msg);
		os.write(CRLF);
	}
}

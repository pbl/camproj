package client;

import gui.ImageMonitor;
//import gui.ModePanel;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import se.lth.cs.eda040.fakecamera.AxisM3006V;

/**
 * Receives data (images and updates to modes) from the server.
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class ClientReciever extends Thread {
	private ImageMonitor im;
	private Socket socket;
	private byte[] jpeg;
	private static final String IS_IMAGE = "1";
	private int ID;

	/**
	 * Creates a new ClientReciever object.
	 * 
	 * @param socket
	 *            The Socket used to talk to the server
	 * @param im
	 *            An ImageMonitor, used to protect the Image buffers and
	 *            different modes
	 */
	public ClientReciever(Socket socket, ImageMonitor im, int ID) {
		this.socket = socket;
		jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
		this.ID = ID;
		this.im = im;
	}

	/**
	 * run-method, launched when .start() is called on an instance of this
	 * class.
	 */
	public void run() {
		System.out.println("ClientReciever started");
		try {
			InputStream is = socket.getInputStream();
			while (!isInterrupted()) {
				String[] msg = parseHeader(is);
				int i = 0;
				for (String s : msg) {
					System.out.println("Client recieved (msg: " + i++ + "): "
							+ s);
				}

				if (msg[0].equals(IS_IMAGE)) { // add image
					parseImage(is);
					im.addImage(ID, jpeg, Long.valueOf(msg[2]).longValue());
				} else { // set movie
					if (!im.isMovieMode()) {
						im.setMovieMode(true);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			//thread has been interrupted
			System.out.println("Interrupted ClientReciever");
//			e.printStackTrace();
		}
	}

	/**
	 * Parses the image part of a message sent to the InputStream.
	 * 
	 * @param is
	 *            The InputStream on which the message is sent
	 * @return 
	 * @throws IOException
	 *             If, for some reason something bad happens
	 */
	private void parseImage(InputStream is) throws IOException, InterruptedException {
		int bufferSize = jpeg.length;
		int bytesRead = 0;
		int bytesLeft = bufferSize;
		int status;
		// We have to keep reading until -1 (meaning "end of file") is
		// returned. The socket (which the stream is connected to)
		// does not wait until all data is available; instead it
		// returns if nothing arrived for some (short) time.
		do {
			if(isInterrupted()){
				throw new InterruptedException();
			}
			status = is.read(jpeg, bytesRead, bytesLeft);


			// The 'status' variable now holds the no. of bytes read,
			// or -1 if no more data is available
			if (status > 0) {
				bytesRead += status;
				bytesLeft -= status;
			}
		} while (status > 0);

	}

	/**
	 * Reads a single line from an InputStream and parses it.
	 * 
	 * @param is
	 *            The InputStream on which the message is sent
	 * @return The Line that was parsed
	 * @throws IOException
	 *             If, for some reason something bad happens
	 */
	private String getLine(InputStream is) throws IOException {
		boolean done = false;
		String result = "";
		while (!done) {
			int ch = is.read(); // Read
			if (ch <= 0 || ch == 10) {
				// Something < 0 means end of data (closed socket)
				// ASCII 10 (line feed) means end of line
				done = true;
			} else if (ch >= ' ') {
				result += (char) ch;
			}
		}
		return result;
	}

	/**
	 * Returns a complete message received from the server.
	 * <ul>
	 * <li>result[0] contains a signifier for the type of message - 0 means
	 * MOVIE, 1 means picture.
	 * <li>result[1] contains the timestamp of the sent image.
	 * <li>result[2] contains the image-data.
	 * </ul>
	 * 
	 * @param is
	 *            The InputStream on which the message was sent
	 * @return The header that was parsed
	 * @throws IOException
	 *             , if for some reason something bad happens
	 */
	private String[] parseHeader(InputStream is) throws IOException {
		String[] result = new String[4];
		int i = 0;
		String msg = "";
		msg = getLine(is);
		while (!(msg.equals("end")) && i < 4) {
			result[i] = msg;
			i++;
			msg = getLine(is);
		}
		return result;
	}

}

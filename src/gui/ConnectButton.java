package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;

import javax.swing.JButton;

import client.ClientReciever;
import client.ClientRequester;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
@SuppressWarnings("serial")
public class ConnectButton extends JButton implements ActionListener {
	private ClientRequester cReq;
	private ClientReciever cRec;
	private Socket socket;
	private static final String DISCONNECTED_BUTTON_TEXT = "Press to connect";
	private static final String CONNECTED_BUTTON_TEXT = "Press to disconnect";
	private ImageMonitor im;
	private int PORT;
	private String SERVER_IP;

	/**
	 * @param SERVER_IP
	 * @param PORT_1
	 * 
	 */
	public ConnectButton(ImageMonitor im, String SERVER_IP, int PORT) {

		super(DISCONNECTED_BUTTON_TEXT);
		this.im = im;
		addActionListener(this);
		this.PORT = PORT;
		this.SERVER_IP = SERVER_IP;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (getText().equals(DISCONNECTED_BUTTON_TEXT)) {
			try {
				socket = new Socket(SERVER_IP, PORT);
				Random rand = new Random();
				// int id = rand.nextInt();
				int id = 1;
				cReq = new ClientRequester(socket, im);
				cRec = new ClientReciever(socket, im, id);
				cReq.start();
				cRec.start();
				System.out.println("Client connecting to port " + PORT
						+ "(through button).");
				setText(CONNECTED_BUTTON_TEXT);
			} catch (ConnectException e) {
				System.out.println("Couldn't connect to " + SERVER_IP
						+ " at port " + PORT + "!");
				System.out
						.println("Make sure the server is started and check that the right port is set");
				System.out.println("Close the program and try again.");
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				System.out.println("Couldn't connect to server at " + SERVER_IP
						+ " on " + PORT + "!");
				e.printStackTrace();
				System.exit(-1);
			}
		} else {
			try {
				cReq.interrupt();
				cReq.join();
				cReq = null;
				System.out.println("ClientRequester closed");
				
				cRec.interrupt();
				cRec.join();
				cRec = null;
				System.out.println("ClientReciever closed");
				
				socket.close();
				socket = null;
				System.out.println("Client disconnecting from port " + PORT
						+ "!");
				setText(DISCONNECTED_BUTTON_TEXT);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				System.out.println("Couldn't close the client socket!");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}

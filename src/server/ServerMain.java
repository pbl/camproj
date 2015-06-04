package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class ServerMain {
	
	private static final int serverPort = 3000;
	/**
	 * main-method, this starts the whole thing.
	 * 
	 * @param args
	 *            args[0] should contain the port on which to talk to the
	 *            client, if not it will yell at you and then exit.
	 */
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("No port given!");
			System.exit(-1);
		}
		int port = Integer.parseInt(args[0]);
		ServerMonitor sm = new ServerMonitor(port);
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("New server socket at " + port);
			while (true) {
				
				Socket socket = serverSocket.accept();
				System.out.println("Client connected");
				ServerSender ss = new ServerSender(sm, socket);
				ServerReceiver sr = new ServerReceiver(sm, socket);
				// MotionDetecter md = new MotionDetecter(sm);
				ss.start();
				sr.start();
				System.out.println("Server started, listening on port " + port);
				try {
					sr.join();
					System.out.println("ServerReciver closed");
					
					ss.join();
					System.out.println("ServerSender closed");
					
					socket.close();
					System.out.println("Server threads has been closed");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
			}
		} catch (IOException e) {
			System.out.println("Error with starting the server socket!");
			e.printStackTrace();
			System.exit(1);
		}
	}
}

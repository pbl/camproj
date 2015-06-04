package gui;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
@SuppressWarnings("serial")
class GUI extends JFrame {

	ImageMonitor im;
	private int PORT_1;
	private int PORT_2;
	private String SERVER_IP;

	/**
	 * @param port2
	 * @param port1
	 * @param server_ip
	 * 
	 */
	public GUI(int port1, int port2, String server_ip) {
		PORT_1 = port1;
		PORT_2 = port2;
		SERVER_IP = server_ip;

		ImagePanel west = new ImagePanel();
		ImagePanel east = new ImagePanel();
		im = new ImageMonitor();

		this.setSize(500, 400);

		Path path = Paths.get("images/loading.jpeg");
		try {
			byte[] loading = Files.readAllBytes(path);
			west.refresh(loading);
			east.refresh(loading);
		} catch (IOException e) {

			e.printStackTrace();
		}

		Panel p1 = new Panel();
		p1.setLayout(new BorderLayout());

		ConnectButton b1 = new ConnectButton(im, SERVER_IP, PORT_1);
		ConnectButton b2 = new ConnectButton(im, SERVER_IP, PORT_2);

		p1.add(b1, BorderLayout.WEST);
		p1.add(b2, BorderLayout.EAST);
		this.getContentPane().add(p1, BorderLayout.NORTH);

		Panel i1 = new Panel();
		i1.setLayout(new BorderLayout());
		i1.add(west);
		this.getContentPane().add(i1, BorderLayout.WEST);

		Panel i2 = new Panel();
		i2.setLayout(new BorderLayout());
		i2.add(east);
		this.getContentPane().add(i2, BorderLayout.EAST);

		Panel p2 = new Panel();
		p2.setLayout(new BorderLayout());
		ModePanel mp = new ModePanel(im);
		SyncPanel sp = new SyncPanel(im);
		mp.addActionListener(mp);
		sp.addActionListener(sp);
		p2.add(mp, BorderLayout.NORTH);
		p2.add(sp, BorderLayout.SOUTH);

		this.getContentPane().add(p2, BorderLayout.SOUTH);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public byte[] read(File file) throws IOException {
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}

}

/**
 * 
 * @author tfla, magnusnorrby, pbl, jagguar
 * 
 */
public class Main {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int port1 = 2000;
		int port2 = 3000;
		String server_ip = "localhost";
		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--server-ip") || args[i].equals("-s")
					&& i < (args.length - 1)) {
				server_ip = (new String(args[i + 1])).toString();
				i++;
				// System.out.println("ip:" + server_ip);
			} else if (args[i].equals("--ports") || args[i].equals("-p")) {
				port1 = (new Integer(args[i + 1])).intValue();
				port2 = (new Integer(args[i + 2])).intValue();
				i++;
				i++;
				// System.out.println("p1:" + port1);
				// System.out.println("p2:" + port2);
			} else if (args[i].equals("--help") || args[i].equals("-h")) {
				System.out.println("Possible starting arguments for main:");
				System.out.println("\tArgument\tMeaning");
				System.out.println("\t-h|--help\tThis help.");
				System.out
						.println("\t-s|--server-ip\tIP for the server, default is \"localhost\".");
				System.out
						.println("\t-p|--ports\tPort numbers to use, default is 2000 and 3000.");
				System.exit(0);
			} else
				System.out.println("Unrecognized command line argument '"
						+ args[i] + "'. Ignored.");
		}
		@SuppressWarnings("unused")
		GUI g = new GUI(port1, port2, server_ip);

	}
}

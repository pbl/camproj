package test;

import gui.ConnectButton;
import gui.ImageMonitor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ConnectButtonGUI {

	public static void main(String[] args) {
		JFrame frame = new JFrame("TestConnect");
		frame.setVisible(true);
		frame.setSize(500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.add(panel);
		ConnectButton cb = new ConnectButton(new ImageMonitor(), "localhost",
				3000);

		panel.add(cb);

	}
}

package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame( ) {
		super("Taxi Company");
		
		setLayout(new BorderLayout());
		
		// set default size
		setSize(600, 500);
		setMinimumSize(new Dimension(500, 400));
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
	
	private DriverFormPanel driverFormPanel;
	
	public MainFrame() {
		super("Taxi Company");
		
		setLayout(new BorderLayout());

		driverFormPanel = new DriverFormPanel();
		
		setJMenuBar(createMenuBar());
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO: disconnect from database when exiting
				dispose();
				System.gc();
			}
			
		});
		
		add(driverFormPanel, BorderLayout.WEST);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Driver Form");
		showFormItem.setSelected(true);

		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		showFormItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
				// TODO: set visibility of driver form
			}
		});

		// TODO: add mnemonics and accelerators

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to quit?", "Confirm",
						JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();

					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});

		return menuBar;
	}
}

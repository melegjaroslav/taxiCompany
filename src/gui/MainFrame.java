package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import controller.Controller;

public class MainFrame extends JFrame {

	private DriverFormPanel driverFormPanel;
	private DriverListPanel driverListPanel;
	private PrefsDialog prefsDialog;
	private Preferences preferences;
	private Controller controller;
	private Toolbar toolbar;

	public MainFrame() {
		super("Taxi Company");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		driverFormPanel = new DriverFormPanel();
		driverListPanel = new DriverListPanel();
		prefsDialog = new PrefsDialog(this);

		preferences = Preferences.userRoot().node("db");

		controller = new Controller();

		driverListPanel.setData(controller.getDrivers());

		driverFormPanel.setDriverFormListener(new DriverFormListener() {
			public void formEventOcurred(DriverFormEvent e) {
				controller.addDriver(e);
				driverListPanel.refresh();
			}
		});

		driverListPanel.setDriverListListener(new DriverListListener() {
			public void rowDeleted(int row) {
				try {
					controller.removeDriver(row);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void toggleAvailable(int row) {
				try {
					controller.toggleAvailable(row);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		prefsDialog.setPrefsListener(new PrefsListener() {
			public void preferencesSet(String user, String password, int port) {
				preferences.put("user", user);
				preferences.put("password", password);
				preferences.putInt("port", port);
			}
		});

		String user = preferences.get("user", "");
		String password = preferences.get("password", "");
		int port = preferences.getInt("port", 3306);
		prefsDialog.setDefaults(user, password, port);

		setJMenuBar(createMenuBar());

		toolbar.setToolbarListener(new ToolbarListener() {

			@Override
			public void connectEventOccured() {
				try {
					controller.connect();
					loadData();
					driverListPanel.refresh();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO: disconnect from database when exiting
				dispose();
				System.gc();
			}

		});

		add(driverFormPanel, BorderLayout.WEST);
		add(toolbar, BorderLayout.NORTH);
		add(driverListPanel, BorderLayout.CENTER);

		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void loadData() {
		try {
			controller.load();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot load drivers.", "Database Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
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

		prefsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prefsDialog.setVisible(true);
			}
		});

		showFormItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem isVisible = (JCheckBoxMenuItem) e.getSource();
				driverFormPanel.setVisible(isVisible.isSelected());
			}
		});

		// set mnemonics
		fileMenu.setMnemonic(KeyEvent.VK_F);
		windowMenu.setMnemonic(KeyEvent.VK_W);

		// set accelerators
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

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

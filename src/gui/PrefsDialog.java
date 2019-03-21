package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PrefsDialog extends JDialog {
	private JButton ok;
	private JButton cancel;
	private JSpinner port;
	private SpinnerNumberModel portModel;
	private JTextField user;
	private JPasswordField password;
	
	private PrefsListener prefsListener;
	
	public PrefsDialog(JFrame parent) {
		super(parent, "Preferences", false);
		
		ok = new JButton("OK");
		cancel = new JButton("Cancel");
		
		portModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		port = new JSpinner(portModel);
		
		user = new JTextField(10);
		password = new JPasswordField(10);
		
		layoutControls();
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int _port = (int)port.getValue();
				String _user = user.getText();
				char[] _password = password.getPassword();
				
				if(prefsListener != null) {
					prefsListener.preferencesSet(_user, new String(_password), _port);
				}
				
				setVisible(false);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		setSize(270, 180);
		setLocationRelativeTo(parent);
	}
	
	public void layoutControls() {
		JPanel controlsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		int space = 5;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");
		
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
		
		controlsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);
		
		/////////// first row //////////
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("User: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(user, gc);
		
		/////////// next row ///////////

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Password: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(password, gc);
		
		/////////// next row ///////////

		gc.gridy++;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightPadding;
		controlsPanel.add(new JLabel("Port: "), gc);

		gc.gridx++;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noPadding;
		controlsPanel.add(port, gc);

		/////////// Buttons Panel ///////////

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(ok);
		buttonsPanel.add(cancel);

		Dimension btnSize = cancel.getPreferredSize();
		ok.setPreferredSize(btnSize);

		// add sub panels to dialog
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	public void setDefaults(String user, String password, int port) {
		this.user.setText(user);
		this.password.setText(password);
		this.port.setValue(port);
	}
	
	public void setPrefsListener(PrefsListener listener) {
		this.prefsListener = listener;
	}
}

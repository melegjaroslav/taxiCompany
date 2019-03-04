package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class DriverFormPanel extends JPanel {
	private DriverFormListener driverFormListener;

	private JTextField firstName;
	private JTextField lastName;
	private JTextField phoneNumber;
	private JSpinner age;
	private SpinnerNumberModel ageModel;

	private JRadioButton male;
	private JRadioButton female;
	private ButtonGroup genderGroup;

	private JList<VehicleType> vehicleType;
	private JTextField vehicleRegPlate;

	private JCheckBox isAvailable;

	private JButton submit;

	public DriverFormPanel() {
		// set width of panel
		Dimension dim = getPreferredSize();
		dim.width = 300;
		setPreferredSize(dim);

		firstName = new JTextField(10);
		lastName = new JTextField(10);
		phoneNumber = new JTextField(10);
		ageModel = new SpinnerNumberModel(18, 18, 68, 1);
		age = new JSpinner(ageModel);

		// set up gender radios
		male = new JRadioButton("male");
		female = new JRadioButton("female");
		
		male.setActionCommand("male");
		female.setActionCommand("female");

		genderGroup = new ButtonGroup();
		genderGroup.add(male);
		genderGroup.add(female);

		DefaultListModel<VehicleType> vehicleTypeModel = new DefaultListModel<VehicleType>();
		vehicleTypeModel.addElement(new VehicleType(0, "Car"));
		vehicleTypeModel.addElement(new VehicleType(0, "Electric Car"));
		vehicleTypeModel.addElement(new VehicleType(0, "Van"));

		vehicleType = new JList<VehicleType>(vehicleTypeModel);

		vehicleType.setPreferredSize(new Dimension(124, 66));
		vehicleType.setBorder(BorderFactory.createEtchedBorder());
		vehicleType.setSelectedIndex(0);

		vehicleRegPlate = new JTextField(10);

		isAvailable = new JCheckBox();

		submit = new JButton("Submit");

		// set up mnemonics
		submit.setMnemonic(KeyEvent.VK_S);

		// add submit action listener
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				String _firstName = firstName.getText();
				String _lastName = lastName.getText();
				String _phoneNumber = phoneNumber.getText();
				int _age = (int) age.getValue();
				String _gender = genderGroup.getSelection().getActionCommand();
				VehicleType _vehicleType = (VehicleType) vehicleType.getSelectedValue();
				String _vehicleRegPlate = vehicleRegPlate.getText();
				boolean _isAvailable = isAvailable.isSelected();

				System.out.println(genderGroup.getSelection().getActionCommand());
				
				DriverFormEvent ev = new DriverFormEvent(this, _firstName, _lastName, _phoneNumber, _age, _gender,
						_vehicleType.getId(), _vehicleRegPlate, _isAvailable);
				if (driverFormListener != null) {
					driverFormListener.formEventOcurred(ev);
				}
			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("Add driver");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		layoutComponents();
	}

	public void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		///////// first row ///////////

		gc.gridy = 0;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("First Name: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(firstName, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Last Name: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(lastName, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Phone Number: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(phoneNumber, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Age: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(age, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(male, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(female, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Vehicle type: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(vehicleType, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("REG plate: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(vehicleRegPlate, gc);

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Available: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		;
		add(isAvailable, gc);
		
		////////next row /////////

		gc.gridy++;

		gc.weightx = 1.0;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(submit, gc);
	}

	public void setDriverFormListener(DriverFormListener listener) {
		this.driverFormListener = listener;
	}
}

class VehicleType {

	private int id;
	private String text;

	public VehicleType(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public int getId() {
		return id;
	}
}

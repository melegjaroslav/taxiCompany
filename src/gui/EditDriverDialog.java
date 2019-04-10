package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class EditDriverDialog extends JDialog {
	private EditDriverListener editDriverListener;

	private String id;
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

	public EditDriverDialog(JFrame parent) {
		super(parent, "Edit Driver", false);

		id = "";
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

		layoutComponents();

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

				EditDriverEvent ev = new EditDriverEvent(this, id, _firstName, _lastName, _phoneNumber, _age, _gender,
						_vehicleType.getId(), _vehicleRegPlate, _isAvailable);
				if (editDriverListener != null) {
					editDriverListener.formEventOcurred(ev);

					setVisible(false);
				}
			}
		});

		setSize(300, 500);
		setLocationRelativeTo(parent);
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

		//////// next row /////////

		gc.gridy++;

		gc.weightx = 1.0;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(submit, gc);
	}

	public void setDefaults(String id, String firstName, String lastName, String phoneNumber, int age, String gender,
			String vehicleRegPlate, boolean isAvailable) {
		this.id = id;
		this.firstName.setText(firstName);
		this.lastName.setText(lastName);
		this.phoneNumber.setText(phoneNumber);
		this.age.setValue(age);
		if (gender == "male") {
			this.male.setSelected(true);
		} else {
			this.female.setSelected(true);
		}
//		this.vehicleType.setSelectedValue(vehicleType, false);
		this.vehicleRegPlate.setText(vehicleRegPlate);
		this.isAvailable.setSelected(isAvailable);
	}

	public void setEditDriverListener(EditDriverListener listener) {
		this.editDriverListener = listener;
	}
}

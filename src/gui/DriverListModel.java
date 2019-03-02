package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Driver;

public class DriverListModel extends AbstractTableModel {

	private List<Driver> db;
	private String[] colNames = { "ID", "First Name", "Last Name", "Phone Number", "Age", "Gender",
			"Vehicle Type", "Vehicle Reg Plate" };
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<Driver> db) {
		this.db = db;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Driver driver = db.get(row);

		switch (col) {
		case 0:
			return driver.getId();
		case 1:
			return driver.getFirstName();
		case 2:
			return driver.getLastName();
		case 3:
			return driver.getPhoneNumber();
		case 4:
			return driver.getAge();
		case 5:
			return driver.getGender();
		case 6:
			return driver.getVehicleType();
		case 7:
			return driver.getVehicleRegPlate();
		}

		return null;
	}
}

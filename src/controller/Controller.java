package controller;

import java.sql.SQLException;
import java.util.List;

import gui.DriverFormEvent;
import model.Database;
import model.Driver;
import model.Gender;
import model.VehicleType;

public class Controller {
	Database db = new Database();
	
	public List<Driver> getDrivers() {
		return db.getDrivers();
	}
	
	public void save() throws SQLException {
		db.save();
	}
	
	public void connect() throws Exception {
		db.connect();
	}
	
	public void load() throws SQLException {
		db.load();
	}

	public void disconnect() {
		db.disconnect();
	}
	
	public void removeDriver(int index) {
		db.removeDriver(index);
	}
	
	public void addDriver(DriverFormEvent e) {
		String firstName = e.getFirstName();
		String lastName = e.getLastName();
		String phoneNumber = e.getPhoneNumber();
		int age = e.getAge();
		String genderCat = e.getGender();
		int vehicleTypeId = e.getVehicleType();
		String vehicleRegPlate = e.getVehicleRegPlate();
		boolean isAvailable = e.isAvailable();
		
		VehicleType vehicleType = null;
		
		switch(vehicleTypeId) {
		case 0: 
			vehicleType = VehicleType.car;
			break;
		case 1:
			vehicleType = VehicleType.electric_car;
			break;
		case 2:
			vehicleType = VehicleType.van;
			break;
		}
		
		Gender gender;
		
		if(genderCat.equals("male")) {
			gender = Gender.male;
		}
		else {
			gender = Gender.female;
		}
		
		Driver driver = new Driver(firstName, lastName, phoneNumber, age, gender, vehicleType, vehicleRegPlate, isAvailable);
		
		db.addDriver(driver);
	}
}

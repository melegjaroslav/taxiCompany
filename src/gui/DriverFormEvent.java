package gui;

import java.util.EventObject;

public class DriverFormEvent extends EventObject {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int age;
	private String gender;
	private int vehicleTypeId;
	private String vehicleRegPlate;
	private boolean isAvailable;

	public DriverFormEvent(Object source) {
		super(source);
	}

	public DriverFormEvent(Object source, String firstName, String lastName, String phoneNumber, int age, String gender,
			int vehicleTypeId, String vehicleRegPlate, boolean isAvailable) {
		super(source);
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.gender = gender;
		this.vehicleTypeId = vehicleTypeId;
		this.vehicleRegPlate = vehicleRegPlate;
		this.isAvailable = isAvailable;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public int getVehicleType() {
		return vehicleTypeId;
	}

	public String getVehicleRegPlate() {
		return vehicleRegPlate;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

}

package model;

import java.io.Serializable;
import java.util.UUID;

public class Driver implements Serializable {

	private static final long serialVersionUID = -232773389738031756L;

	private String id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int age;
	private Gender gender;
	private VehicleType vehicleType;
	private String vehicleRegPlate;
	private boolean isAvailable;

	public Driver(String firstName, String lastName, String phoneNumber, int age, Gender gender,
			VehicleType vehicleType, String vehicleRegPlate, boolean isAvailable) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.gender = gender;
		this.vehicleType = vehicleType;
		this.vehicleRegPlate = vehicleRegPlate;
		this.isAvailable = isAvailable;
		
		this.id = UUID.randomUUID().toString();
	}
	
	public Driver(String id, String firstName, String lastName, String phoneNumber, int age, Gender gender,
			VehicleType vehicleType, String vehicleRegPlate, boolean isAvailable) {
		
		this(firstName, lastName, phoneNumber, age, gender,vehicleType, vehicleRegPlate, isAvailable);
		
		this.id = id;
	}
	
	public static int getCount() {
//		return count;
//		TODO: count
		return 0;
	}

	public String getId() {
		return id;
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

	public Gender getGender() {
		return gender;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public String getVehicleRegPlate() {
		return vehicleRegPlate;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

}

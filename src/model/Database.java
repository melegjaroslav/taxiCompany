package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	
	private List<Driver> drivers;
	private Connection connection;
	
	public Database() {
		drivers = new LinkedList<Driver>();
	}
	
	public void connect() throws Exception {
		
		if(connection != null) return;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:mysql://localhost:3306/taxicompany";
		connection = DriverManager.getConnection(url, "root", "toor");
		
		System.out.println("Connected: " + connection);
		
	}
	
	public void disconnect() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}
	
	public void save(Driver driver) throws SQLException {
		
//		String checkSql = "select count(*) as count from drivers where id=?";
		
//		PreparedStatement checkStatement = connection.prepareStatement(checkSql);
		
		String insertSql = "insert into drivers (id, first_name, last_name, phone_number, age, gender, vehicle_type, vehicle_reg_plate, available) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = connection.prepareStatement(insertSql);
		
//		String updateSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
//		PreparedStatement updateStatement = con.prepareStatement(updateSql);

		String id = driver.getId();
		String firstName = driver.getFirstName();
		String lastName = driver.getLastName();
		String phoneNumber = driver.getPhoneNumber();
		int age = driver.getAge();
		Gender gender = driver.getGender();
		VehicleType vehicleType = driver.getVehicleType();
		String vehicleRegPlate = driver.getVehicleRegPlate();
		boolean isAvailable = driver.isAvailable();
		
//		checkStatement.setString(1, id);
		
//		ResultSet checkResult = checkStatement.executeQuery();
//		checkResult.next();
		
//		int count = checkResult.getInt(1);
		
		System.out.println("Inserting driver with ID " + id);
		
		int col = 1;
		insertStatement.setString(col++, id);
		insertStatement.setString(col++, firstName);
		insertStatement.setString(col++, lastName);
		insertStatement.setString(col++, phoneNumber);
		insertStatement.setInt(col++, age);
		insertStatement.setString(col++, gender.name());
		insertStatement.setString(col++, vehicleType.name());
		insertStatement.setString(col++, vehicleRegPlate);
		insertStatement.setBoolean(col, isAvailable);
		
		insertStatement.executeUpdate();

//		} else {
//			System.out.println("Updating person with ID " + id);
//			
//			int col = 1;
//			updateStatement.setString(col++, name);
//			updateStatement.setString(col++, age.name());
//			updateStatement.setString(col++, emp.name());
//			updateStatement.setString(col++, tax);
//			updateStatement.setBoolean(col++, isUs);
//			updateStatement.setString(col++, gender.name());
//			updateStatement.setString(col++, occupation);				
//			updateStatement.setInt(col++, id);
//			
//			updateStatement.executeUpdate();
//		}
		
//		updateStatement.close();
		insertStatement.close();
//		checkStatement.close();
	}
	
	public void load() throws SQLException {
		drivers.clear();
		
		String sql = "select id, first_name, last_name, phone_number, age, gender, vehicle_type, vehicle_reg_plate, available from drivers order by first_name";
		Statement selectStatement = connection.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()) {
			String id = results.getString("id");
			String firstName = results.getString("first_name");
			String lastName = results.getString("last_name");
			String phoneNumber = results.getString("phone_number");
			int age = results.getInt("age");
			String gender = results.getString("gender");
			String vehicleType = results.getString("vehicle_type");
			String vehicleRegPlate = results.getString("vehicle_reg_plate");
			boolean isAvailable = results.getBoolean("available");
			
			Driver driver = new Driver(id, firstName, lastName, phoneNumber, age, Gender.valueOf(gender), VehicleType.valueOf(vehicleType), vehicleRegPlate, isAvailable); 
			drivers.add(driver);
		}
		
		results.close();
		selectStatement.close();
	}
	
	public void addDriver(Driver driver) {
		drivers.add(driver);
		try {
			save(driver);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Driver> getDrivers() {
		return Collections.unmodifiableList(drivers);
	}
	
	public void removeDriver(int index) throws SQLException {
		String driverId = drivers.get(index).getId();
		
		String removeSql = "delete from drivers WHERE id = ?";
		PreparedStatement removeStatement = connection.prepareStatement(removeSql);
		
		removeStatement.setString(1, driverId);
		
		removeStatement.executeUpdate();
		
		drivers.remove(index);
		
		removeStatement.close();
	}

	public void toggleAvailable(int index) throws SQLException {
		String driverId = drivers.get(index).getId();
		boolean isAvailable = !drivers.get(index).isAvailable();
		
		String toggleAvailableSql = "update drivers set available = ? where id = ?";
		PreparedStatement toggleAvailableStatement = connection.prepareStatement(toggleAvailableSql);
		
		int col = 1;
		toggleAvailableStatement.setBoolean(col++, isAvailable);
		toggleAvailableStatement.setString(col, driverId);
		
		toggleAvailableStatement.executeUpdate();
		
		drivers.get(index).setAvailable(isAvailable);
		
		toggleAvailableStatement.close();
	}
}

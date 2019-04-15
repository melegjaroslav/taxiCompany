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
import java.util.prefs.Preferences;

public class Database {
	
	private List<Driver> drivers;
	private Connection connection;
	private Preferences preferences;
	
	public Database() {
		//initialize drivers list
		drivers = new LinkedList<Driver>();
		
		// get preferences from saved preferences
		preferences = Preferences.userRoot().node("db");
	}
	
	public void connect() throws Exception {
		// if connection already established, do nothing
		if(connection != null) return;
		
		// try to find connector, throw exception if not found
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:mysql://localhost:" + preferences.getInt("port", 3306) + "/taxicompany";
		// connect to database, save connection to variable so it can be closed
		connection = DriverManager.getConnection(url, preferences.get("user", ""), preferences.get("password", ""));
	}
	
	public void disconnect() {
		// if connection exists, close it
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}
	
	private void save(Driver driver) throws SQLException {
		
		String insertSql = "insert into drivers (id, first_name, last_name, phone_number, age, gender, vehicle_type, vehicle_reg_plate, available) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = connection.prepareStatement(insertSql);

		String id = driver.getId();
		String firstName = driver.getFirstName();
		String lastName = driver.getLastName();
		String phoneNumber = driver.getPhoneNumber();
		int age = driver.getAge();
		Gender gender = driver.getGender();
		VehicleType vehicleType = driver.getVehicleType();
		String vehicleRegPlate = driver.getVehicleRegPlate();
		boolean isAvailable = driver.isAvailable();
		
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

		insertStatement.close();
	}
	
	private void update(Driver driver) throws SQLException {
		String updateSql = "update drivers set first_name=?, last_name=?, phone_number=?, age=?, gender=?, vehicle_reg_plate=?, available=?  where id=?";
		PreparedStatement updateStatement = connection.prepareStatement(updateSql);
		
		String id = driver.getId();
		String firstName = driver.getFirstName();
		String lastName = driver.getLastName();
		String phoneNumber = driver.getPhoneNumber();
		int age = driver.getAge();
		Gender gender = driver.getGender();
//		VehicleType vehicleType = driver.getVehicleType();
		String vehicleRegPlate = driver.getVehicleRegPlate();
		boolean isAvailable = driver.isAvailable();
		
		System.out.println("Updating person with ID " + id);
		
		int col = 1;
		updateStatement.setString(col++, firstName);
		updateStatement.setString(col++, lastName);
		updateStatement.setString(col++, phoneNumber);
		updateStatement.setInt(col++, age);
		updateStatement.setString(col++, gender.name());
		updateStatement.setString(col++, vehicleRegPlate);
		updateStatement.setBoolean(col++, isAvailable);				
		updateStatement.setString(col++, id);
		
		updateStatement.executeUpdate();
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
	
	public void updateDriver(Driver driver) {
		try {
			update(driver);
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

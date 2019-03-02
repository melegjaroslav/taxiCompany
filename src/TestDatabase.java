import java.sql.SQLException;

import model.Database;
import model.Driver;
import model.Gender;
import model.VehicleType;

public class TestDatabase {
	public static void main(String[] args) {
		System.out.println("Running database test");
		
		Database db = new Database();
		
		try {
			db.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.addDriver(new Driver("John", "Doe", "+33 344 455", 33, Gender.male, VehicleType.electric_car, "GG 456LL", true));
		
		try {
			db.save();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			db.load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.disconnect();
	}
}

package gui;

public interface DriverListListener {
	public void rowDeleted(int row);
	public void toggleAvailable(int row);
	public void editDriver(int row);
}

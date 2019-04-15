package gui;

import java.util.EventListener;

public interface DriverListListener extends EventListener {
	public void rowDeleted(int row);
	public void toggleAvailable(int row);
	public void editDriver(int row);
}

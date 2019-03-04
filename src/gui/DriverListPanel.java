package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Driver;

public class DriverListPanel extends JPanel {
	
	private JTable drivers;
	private DriverListModel driverListModel;
	private JPopupMenu popup;
	private DriverListListener driverListListener;
	
	public DriverListPanel() {
		driverListModel = new DriverListModel();
		drivers = new JTable(driverListModel);
		popup = new JPopupMenu();
		
		JMenuItem removeItem = new JMenuItem("Delete Driver");
		JMenuItem toggleAvailable = new JMenuItem("Toggle Available");
		
		popup.add(toggleAvailable);
		popup.add(removeItem);
		
		drivers.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				int row = drivers.rowAtPoint(e.getPoint());
				
				drivers.getSelectionModel().setSelectionInterval(row, row);
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					popup.show(drivers, e.getX(), e.getY());
				}
			}
		});
		
		toggleAvailable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = drivers.getSelectedRow();
				
				if(driverListListener != null) {
					driverListListener.toggleAvailable(row);
					driverListModel.fireTableRowsUpdated(row, row);
				}
			}
		});
		
		removeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = drivers.getSelectedRow();
				
				if(driverListListener != null) {
					driverListListener.rowDeleted(row);
					driverListModel.fireTableRowsDeleted(row, row);
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(drivers), BorderLayout.CENTER);
	}
	
	public void setData(List<Driver> db) {
		driverListModel.setData(db);
	}
	
	public void refresh() {
		driverListModel.fireTableDataChanged();
	}
	
	public void setDriverListListener(DriverListListener listener) {
		this.driverListListener = listener;
	}
}

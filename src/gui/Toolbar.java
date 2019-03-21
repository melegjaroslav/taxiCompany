package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener {

	private JButton connect;
	private ToolbarListener databaseListener;
	
	public Toolbar() {
		setBorder(BorderFactory.createEtchedBorder());
		
		connect = new JButton("Connect");
		
		connect.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(connect);
	}
	
	public void setToolbarListener(ToolbarListener listener) {
		this.databaseListener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if(clicked == connect) {
			if(databaseListener != null) {
				databaseListener.connectEventOccured();
			}
		}
	}
	
}

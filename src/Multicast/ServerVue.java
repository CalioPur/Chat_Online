package Multicast;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ServerVue extends JFrame {
	
	
	public ServerVue(JPanel panel){
		super("ServerLog");
		this.setResizable(false);
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);
		//this.add(panel, BorderLayout.CENTER);
		this.setSize(1920/2,1080/2);
		this.setVisible(true);
	}
	
}

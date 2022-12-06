package Multicast;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ClientVue extends JFrame {
	
	
	public ClientVue(JPanel panel, JTextField textField, JButton button){
		super("Discord 2");
		this.setResizable(false);
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel2 = new JPanel();
		textField.setSize(1920/2,20);
		panel2.add(textField, BorderLayout.CENTER);
		panel2.add(button, BorderLayout.EAST);
		this.add(panel2, BorderLayout.SOUTH);
		this.setSize(1920/2,1080/2);
		this.setVisible(true);
	}
}

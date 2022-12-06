package Multicast;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientThread extends Thread {
	
	private Socket socket;
	private BufferedReader input;
	private JPanel panel;
	private JTextField textField;
	private JButton send;
	public ClientThread(Socket s, JPanel panel, JTextField textField, JButton send) throws IOException {
		this.socket = s;
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.panel = panel;
		this.textField = textField;
		this.send = send;
		
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				String reponse = input.readLine();
				System.out.println(reponse);
				panel.add(new JLabel(reponse), BorderLayout.WEST);
				panel.updateUI();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				input.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package Multicast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainClient implements MouseListener{
	
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	Scanner scanner;
	
	String userInput;
	String reponse;
	String clientName;
	
	JPanel display;
	JTextField textField;
	JButton send;
	
	ClientVue vue;
	public MainClient() throws IOException{
		socket = new Socket("localhost", 5000);
		input = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream())); 
		output = new PrintWriter(socket.getOutputStream(), true);
		scanner = new Scanner(System.in); 
		
		clientName = "none";
		
		display = new JPanel();
		display.setBackground(Color.WHITE);
		display.setLayout(new GridLayout(0,1,10,0));
		textField = new JTextField(70);
		send= new JButton("envoyer");
		send.addMouseListener(this);
		vue = new ClientVue(display, textField, send);
	}
	public static void main(String[] args) {
		try {
			MainClient main  = new MainClient();
			ClientThread clientThread = new ClientThread(main.socket, main.display, main.textField, main.send);
			clientThread.start();
			main.display.add(new JLabel("<html><font color='red'>|SYSTEM|: </font> Chose a username</html>"), BorderLayout.WEST);
			/*do {
				if(main.clientName.equals("none")) {
					System.out.println("please enter your name");
					main.userInput = main.scanner.nextLine();
					main.clientName = main.userInput;
					main.output.println(main.userInput + " entered the chat");
				}
				else {
					String message = ("|"+main.clientName +"| :");
					//System.out.println(message);
					main.userInput = main.scanner.nextLine();
					main.output.println(message + " " + main.userInput);
					
					if (main.userInput.equals("exit")) {
						break;
					}
				}
			}while (!main.userInput.equals("exit"));*/
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(clientName.equals("none")) {
			userInput = textField.getText();
			clientName = userInput;
			textField.setText("");
			output.println(userInput + " entered the chat");
		}
		else {
			String message = ("|"+clientName +"| :");
			//System.out.println(message);
			userInput = textField.getText();
			textField.setText("");
			output.println(message + " " + userInput);
			if(userInput.equals("exit")){
				System.exit(0);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

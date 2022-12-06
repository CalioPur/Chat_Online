package Multicast;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ServerThread extends Thread{
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private PrintWriter output;
	private JPanel panel;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threads, JPanel panel) {
		this.socket = socket;
		this.threadList = threads;
		this.panel = panel;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			while(true) {
				String outputString = input.readLine();
				if(outputString.equals("exit")) {
					break;
				}
				printToAllClients(outputString);
				System.out.println("Server received : " + outputString);
				panel.add(new JLabel(outputString), BorderLayout.WEST);
				panel.updateUI();
				
			}
		}
		catch(Exception e) {
			System.out.println("error occured in main of server : "+ e.getStackTrace());
		}
	}
	
	private void printToAllClients(String outputString) {
		for(ServerThread sT : threadList) {
			sT.output.println(outputString);
		}
	}

}

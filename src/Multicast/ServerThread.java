package Multicast;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ServerThread extends Thread{
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private PrintWriter output;
	private JPanel panel;
	
	//the private key is kept by the server and must not be shared
	private PrivateKey privateKey;
	
	private RSAModule rsa;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threads, JPanel panel, PrivateKey key) {
		this.socket = socket;
		this.threadList = threads;
		this.panel = panel;
		this.privateKey = key;
		this.rsa = new RSAModule();
	}
	
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			
			String outputString,decryptedOutputString;
			
			while(true) {
				outputString = input.readLine();
				
				//the output is encrypted - it is decrypted with the PRIVATE key
				decryptedOutputString = rsa.decryptMessage(outputString, privateKey);
				
				if(decryptedOutputString.toLowerCase().equals("exit")) {
					break;
				}
				printToAllClients(decryptedOutputString);
				System.out.println("Server received (encrypted): " + outputString);
				System.out.println("Server received (decrypted): " + decryptedOutputString);
				panel.add(new JLabel(decryptedOutputString), BorderLayout.PAGE_START);
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

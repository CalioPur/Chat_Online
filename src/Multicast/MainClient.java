package Multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 5000)){
			BufferedReader input = new BufferedReader(new java.io.InputStreamReader(socket.getInputStream())); 
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in); 
			
			String userInput;
			String reponse;
			String clientName = "none";
			
			ClientThread clientThread = new ClientThread(socket);
			clientThread.start();
			
			do {
				if(clientName.equals("none")) {
					System.out.println("please enter your name");
					userInput = scanner.nextLine();
					clientName = userInput;
					output.println(userInput + "entered the chat");
				}
				else {
					String message = ("|"+clientName +"| :");
					//System.out.println(message);
					userInput = scanner.nextLine();
					output.println(message + " " + userInput);
					
					if (userInput.equals("exit")) {
						break;
					}
				}
			}while (!userInput.equals("exit"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

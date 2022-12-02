package Multicast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServeur {
	public static void main(String[] args) {
		ArrayList<ServerThread> threadList = new ArrayList<>();
		try(ServerSocket serverSocket = new ServerSocket(5000)){
			Socket socket = serverSocket.accept();
			ServerThread serverThread= new ServerThread(socket, threadList);
			threadList.add(serverThread);
			serverThread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

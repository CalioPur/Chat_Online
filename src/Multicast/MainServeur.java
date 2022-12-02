package Multicast;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServeur {
    public static void main(String[] args) {
    	ArrayList<ServerThread> MyThread = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            for (;;) {
                Socket socket = serverSocket.accept();
                ServerThread s = new ServerThread(socket, MyThread);
                MyThread.add(s);
                s.start();
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }
}

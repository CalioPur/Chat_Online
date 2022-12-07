package Multicast;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainServeur {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
    	ArrayList<ServerThread> MyThread = new ArrayList<>();
    	JPanel panel = new JPanel();
    	GridLayout grl = new GridLayout(0,1,10,0);
    	panel.setLayout(grl);
    	
    	//creates keys
    	RSAModule rsa = new RSAModule();
    	
    	//the private key is kept by the server and must not be shared
    	PrivateKey privateKey = rsa.generateKeys();
    	
    	ServerVue vue = new ServerVue(panel);
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            for (;;) {
                Socket socket = serverSocket.accept();
                ServerThread s = new ServerThread(socket, MyThread, panel, privateKey);
                MyThread.add(s);
                s.start();
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }
}

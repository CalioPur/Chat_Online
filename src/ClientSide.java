import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClientSide {

	public void Launch() throws NoSuchAlgorithmException{
 
		Socket clientSocket;
		BufferedReader in;
		PrintWriter out;
		Scanner sc = new Scanner(System.in);//pour lire � partir du clavier
		
		AESModule aes = new AESModule();
		Key key;
 
		try {
			
			clientSocket = new Socket("10.163.3.22",4444);
 
			//flux pour envoyer
			out = new PrintWriter(clientSocket.getOutputStream());
			//flux pour recevoir
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//create key
			key = aes.generateKey();
 
			Thread envoyer = new Thread(new Runnable() {
				String msg;
				@Override
				public void run() {
					while(true){
						msg = sc.nextLine();
						out.println(msg);
						out.flush();
						
						//disconnects client
						//(to lower case to prevent case sensitivity)
						if(msg.toLowerCase().equals("bye")) System.exit(0);
						
					}
				}
			});
			envoyer.start();
 
			Thread recevoir = new Thread(new Runnable() {
				String msg;
				@Override
				public void run() {
					try {
						msg = in.readLine();
						while(msg!=null){
							System.out.println("Serveur : "+msg);
							msg = in.readLine();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			recevoir.start();
 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
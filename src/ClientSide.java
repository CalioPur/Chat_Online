import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class ClientSide {

	public void Launch() throws NoSuchAlgorithmException{
 
		Socket clientSocket;
		BufferedReader in;
		PrintWriter out;
		Scanner sc = new Scanner(System.in);//pour lire � partir du clavier
		
		AESModule aes = new AESModule();
		Key key;
 
		try {
			
			clientSocket = new Socket("localhost",4444);
 
			//flux pour envoyer
			out = new PrintWriter(clientSocket.getOutputStream());
			//flux pour recevoir
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//create key 
			key = aes.generateKey();
			
			//you may import key from file as well
			//key = aes.importKeyFromFile();
 
			Thread envoyer = new Thread(new Runnable() {
				String msg;
				String cryptedMsg;
				@Override
				public void run() {
					while(true){
						msg = sc.nextLine();
						
						try {
							cryptedMsg = aes.encryptMessage(msg, key);
						} catch (InvalidKeyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalBlockSizeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BadPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//out.println(msg);
						out.println(cryptedMsg);
						
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
							//crypted
							System.out.println("Serveur : "+msg);
							//decrypted
							System.out.println("Serveur (decrypted) : "+aes.decryptMessage(msg, key));
							
							msg = in.readLine();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidKeyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalBlockSizeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadPaddingException e) {
						// TODO Auto-generated catch block
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
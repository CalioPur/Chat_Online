import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class ServerSide {
 
	public void Launch(){
 
		ServerSocket serveurSocket ;
		Socket clientSocket ;
		BufferedReader in;
		PrintWriter out;
		Scanner sc=new Scanner(System.in);
		
		AESModule aes = new AESModule();
		Key key;
	 
		try {
			serveurSocket = new ServerSocket(4444);
			clientSocket = serveurSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
			
			//on creer deux thread pour pouvoir envoyer un message et recevoir un message 
			//sans avoir a alterner l'un et lautre a chaque fois si elle etait dans la meme boucle while true
			
			//ask for key from file
			key = aes.importKeyFromFile();
			
			Thread envoi= new Thread(new Runnable() {
				String msg;
				String cryptedMsg;
				
				@Override
				public void run() {
					while(true){
						msg = sc.nextLine();
						
						//sends crypted message instead of classic message using the shared key
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
					}
				}
			});
			envoi.start();
	 
			Thread recevoir= new Thread(new Runnable() {
				String msg ;
				
				@Override
				public void run() {
					try {
						msg = in.readLine();
						//tant que le client est connect�
						while(msg!=null){
							//crypted
							System.out.println("Client : "+msg);
							//decrypted
							System.out.println("Client (decrypted) : "+aes.decryptMessage(msg, key));
							msg = in.readLine();
						}
					} 
					catch (IOException e) {
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSide {
 
	public void Launch(){
 
		ServerSocket serveurSocket ;
		Socket clientSocket ;
		BufferedReader in;
		PrintWriter out;
		Scanner sc=new Scanner(System.in);
	 
		try {
			serveurSocket = new ServerSocket(4444);
			clientSocket = serveurSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
			
			//on creer deux thread pour pouvoir envoyer un message et recevoir un message 
			//sans avoir a alterner l'un et lautre a chaque fois si elle etait dans la meme boucle while true
			
			Thread envoi= new Thread(new Runnable() {
				String msg;
				
				@Override
				public void run() {
					while(true){
						msg = sc.nextLine();
						out.println(msg);
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
							System.out.println("Client : "+msg);
							msg = in.readLine();
						}
					} 
					catch (IOException e) {
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import  java.net.Socket;
public class ServerSide {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(4444);
			Socket clientSocket = serverSocket.accept();
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String str = bfr.readLine();
			while (str != null) {
				System.out.println(str);
				str = bfr.readLine();
			}
			
			
			//System.out.println("aaaa");
		}
		catch(IOException e) {
			System.out.println("could not listen on port 4444");
			System.exit(-1);
		}
		

	}
}

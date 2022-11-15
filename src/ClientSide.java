import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSide {

	public static void main(String[] args) {
		
		Socket echoSocket;
		PrintWriter out;
		BufferedReader in;
		BufferedWriter test;
		Scanner sc;
		
		try{
			echoSocket = new Socket("127.0.0.1",4444) ;
			out = new PrintWriter(echoSocket.getOutputStream(),true) ;
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())) ;
			while(true) {
				sc = new Scanner(System.in);
				String str = sc.nextLine();
				out.println(str);
				out.flush();
			}
			

		}
		catch(UnknownHostException e){
			System.out.println("destination inconue") ;
			System.exit(-1) ;
		}
		catch(IOException e){
			System.out.println("je comprend pas cette erreur") ;
			System.exit(-1) ;
		}

	}

}


import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.DatagramPacket;
    import java.net.DatagramSocket;
    import java.net.InetAddress;
    import java.net.ServerSocket;
    import java.net.Socket;
    import java.net.SocketException;
    import java.util.Scanner;


    public class ClientMulticast {
        private static int portMulticasting = 1004;

        private DatagramSocket socket;
        private boolean broadcast =  true;
        private String group = "230.0.0.1"; //group address
        private int delay = 5000;

        public ClientMulticast(){
            try{
                socket = new DatagramSocket();
                System.out.println("agent ready");
            }
            catch (SocketException e){
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void start(String agentName){
            DatagramPacket packet;
            try{
                InetAddress address = InetAddress.getByName(group);
                while (broadcast){
                    byte[] buf = new byte[256];
                    buf = agentName.getBytes();
                    packet = new DatagramPacket(buf,buf.length,address,portMulticasting);
                    socket.send(packet);
                    try{
                        Thread.sleep(delay);
                    } catch (InterruptedException e){
                        System.exit(0);
                    }   
                }
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        public static void main (String[] args) throws IOException {

            System.out.println("Enter name of the new agent: ");
            Scanner sc = new Scanner(System.in);
            String agentName = sc.nextLine();

            ClientMulticast agent = new ClientMulticast();
            agent.start(agentName);
       }
   }


import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.DatagramPacket;
    import java.net.InetAddress;
    import java.net.MulticastSocket;
    import java.net.Socket;
    import java.net.UnknownHostException;
    import java.util.Scanner;



    public class ServMulticast {

        public static void main(String[] args) throws IOException {
            String groupIP = "230.0.0.1";
            int portMulticasting = 1004;
            if (args.length > 0)
                groupIP = args[0];
            try{
                MulticastSocket socket = new MulticastSocket(portMulticasting);
                InetAddress group = InetAddress.getByName(groupIP);
                socket.joinGroup(group);
                //get packet
                DatagramPacket packet;
                while (true){
                    byte[] buf = new byte[256];
                    packet = new DatagramPacket(buf,buf.length);
                    socket.receive(packet);
                    buf = packet.getData();
                    int len = packet.getLength();
                    String received = (new String(buf)).substring(0,len);
                    try{
                        System.out.println("Agent name: " + received);
                    } catch (NumberFormatException e){
                        System.out.println("cannot interpret number");
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
   }
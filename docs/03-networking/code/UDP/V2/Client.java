/**
 * A simple Datagram client
 */
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String arg[]) {
        try {
            // Creating the multicast socket
            MulticastSocket socket = new MulticastSocket(12345);
            // Joining the group 
            //socket.joinGroup(InetAddress.getByName("224.0.0.0"));
            InetAddress mcastaddr = InetAddress.getByName("224.0.0.0");
            socket.joinGroup(new InetSocketAddress(mcastaddr, 0), null);

            while(true){
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String answer = new String(packet.getData(), 0, packet.getLength());
                System.out.println(answer);
            }

        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

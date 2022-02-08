/**
 * A simple Datagram client
 */
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String arg[]) {
        try {
            // Creating the socket (without a port --chose an available port)
            DatagramSocket socket = new DatagramSocket();

            // Preparing the request to the server
            byte[] buffer = new byte[256];
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 12345);
            socket.send(packet);

            // Receiving the answer from the server
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String answer = new String(packet.getData(), 0, packet.getLength());
            System.out.println(answer);
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

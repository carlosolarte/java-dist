/**
 * A Datagram Server
 *  It send the current date and time
 */

import java.net.*; 
import java.io.*;
import java.util.*;


public class Server extends Thread{
    public static final int PORT = 12345;
    private DatagramSocket socket;

    public Server() throws IOException{
        socket = new DatagramSocket(Server.PORT);
    }

    public void run(){
        while(true){
            try{
                // receive request
                byte [] buffer = new byte[256];
                // Datagram for receiving packets 
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                // Sending a message
                InetAddress address = packet.getAddress(); // Address of the incoming packet
                int port = packet.getPort(); // port of the incoming packet

                System.out.println("[CLIENT]: " + address + " : " + port);

                byte [] output = new Date().toString().getBytes();
                packet = new DatagramPacket(output, output.length, address, port);
                socket.send(packet);
            }
            catch (Exception E){
                System.out.println(E);
            }
        }
    }

    public static void main(String arg[]) {
        try {
            Server S = new Server();
            S.start();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

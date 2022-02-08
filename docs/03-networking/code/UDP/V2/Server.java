/**
 * A Multicast Datagram Server
 *  It send the current date and time
 */

import java.net.*; 
import java.io.*;
import java.util.*;


public class Server extends Thread{
    public static final int PORT = 12345;
    private Random R = new Random();
    private DatagramSocket socket;

    public Server() throws IOException{
        socket = new DatagramSocket();
    }

    public void run(){
        while(true){
            try{
                // Sending a message
                InetAddress address = InetAddress.getByName("224.0.0.0"); 

                byte [] output = new Date().toString().getBytes();
                DatagramPacket packet = new DatagramPacket(output, output.length, address, Server.PORT);
                socket.send(packet);
                Thread.sleep(R.nextInt(4000));
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

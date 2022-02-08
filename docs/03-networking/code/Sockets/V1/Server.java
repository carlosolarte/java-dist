/**
 * A simple server that listens on port 12345
 * after the connection, both client and server
 * terminate
 */

import java.net.*; 
import java.io.*;

public class Server {

    public static void main(String arg[]) {
        ServerSocket server;
        try {
            //  Opening the port
            server = new ServerSocket(12345);

            System.out.println("Waiting for connections");
            // Accept blocks until a new connection arrives
            Socket client = server.accept();
            // Print some information from the client
            System.out.println("Client [connected]: ");
            System.out.println("Address: " +  client.getInetAddress().getHostAddress());
            System.out.println("Port: " +  client.getPort());

            // Ending the service
            server.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

/**
 * A server that receives a message from the client and
 * prints it 
 */
import java.net.*;
import java.io.*;

public class Server {

    public static void main(String arg[]) {
        ServerSocket server;
        try {
            server = new ServerSocket(12345);
            System.out.println("Waiting for connections...");
            Socket client = server.accept();
            System.out.println("Connection  [OK] ");

            // Create an input stream from the input stream of the socket
            DataInputStream input_client = new DataInputStream(client.getInputStream());
            System.out.println("Message: " + input_client.readUTF());

            server.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

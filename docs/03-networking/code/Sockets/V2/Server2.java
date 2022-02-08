/**
 * A server that receives a message from the client and
 * prints it 
 * This version uses BufferedReader and hence, the client can be 
 * implemented in Python
 */
import java.net.*;
import java.io.*;

public class Server2 {

    public static void main(String arg[]) {
        ServerSocket server;
        try {
            server = new ServerSocket(12345);
            System.out.println("Waiting for connections...");
            Socket client = server.accept();
            System.out.println("Connection  [OK] ");

            BufferedReader input_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String txt = input_client.readLine();
            System.out.println("Message: " + txt);

            server.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

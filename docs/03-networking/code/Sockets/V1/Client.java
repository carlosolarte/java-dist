/**
 * A simple client that connects to the server
 */
import java.io.*;
import java.net.*;

public class Client {

    public static void main(String arg[]) {
        try {
            // Establishing a connection with the server
            Socket s_client = new Socket("127.0.0.1", 12345);
            System.out.println("Connection [OK]");
            // Closing the socket
            s_client.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

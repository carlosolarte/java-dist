/**
 * A client sending a message to the server
 */

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String arg[]) {
        try {
            Socket s_client = new Socket("127.0.0.1", 12345);
            System.out.println("Connection [OK]");

            // Creating an outputstream to send data
            DataOutputStream output = new DataOutputStream(s_client.getOutputStream());
            // Send a message 
            output.writeUTF("Hello World... now on a socket ;-)");
            System.out.println("Message sent!");


            s_client.close();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

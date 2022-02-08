/**
 * A client that creates a new person
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Client {

    public static void main(String arg[]) {
        if(arg.length !=2){
            System.out.println("Missing argument. ");
            System.out.println("Usage: java Client <name> <yyyy/mm/dd>");
            System.exit(1);
        }

        try {
            String name = arg[0];
            SimpleDateFormat DF = new SimpleDateFormat("yyyy/mm/dd");
            Date date = DF.parse(arg[1]);
            Socket s_client = new Socket("127.0.0.1", 12345);
            System.out.println("Connection [OK]");

            // Creating an outputstream to send data
            ObjectOutputStream output = new ObjectOutputStream(s_client.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(s_client.getInputStream());
            // Send a message 
            output.writeUTF(name);
            output.writeObject(date);
            // Receiving the ID
            UUID id = (UUID) input.readObject();
            System.out.println("ID obtained: " + id );


            s_client.close();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}

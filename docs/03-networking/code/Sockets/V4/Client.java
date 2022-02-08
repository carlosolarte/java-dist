/**
 * A client that creates a new person
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.util.concurrent.*;

public class Client {
    public static final String SERVER = "127.0.0.1";
    public static final int PORT = 12345 ;

    public static UUID createPerson(String name, Date date)
            throws IOException,ClassNotFoundException {
            Socket s_client = new Socket(Client.SERVER, Client.PORT);
            System.out.println("Connection [OK]");

            // Creating an outputstream to send data
            ObjectOutputStream output = new ObjectOutputStream(s_client.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(s_client.getInputStream());
            // Send a message 
            output.writeUTF(name);
            output.writeObject(date);
            // Receiving the ID
            UUID id = (UUID) input.readObject();
            s_client.close();
            return id;
    }

    public static void main(String arg[]) {
        // Here we simulate a client that sends
        // several requests to the server to 
        // create new people in the database
        // Note that the requests are asynchronous
        Random R = new Random(System.currentTimeMillis());
        int NPEOPLE = 20;

        try {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            CompletionService<UUID> completionService = 
                new ExecutorCompletionService<>(executor);

            // Creating NPEOPLE random people
            for(int i = 1; i <= NPEOPLE; i++) {
                completionService.submit(new Callable<UUID>() {
                    public UUID call() {
                        try{
                            String name = "Person-" + (R.nextInt()%1000);
                            // Up to approx 20 years ago
                            long L1 = Math.abs(R.nextLong()) % (20*365*24*3600*1000L);
                            Date D = new Date(System.currentTimeMillis() - L1);
                            UUID id = Client.createPerson(name, D);
                            return id;
                        }
                        catch(Exception E){ }
                        return null;
                    }
                }
                );
            }
            // Receiving and printing the IDs created
            for(int i = 1; i <= NPEOPLE; i++) {
                // take blocks until a new element is available
                Future<UUID> result = completionService.take(); 
                System.out.println(result.get());
            }
            executor.shutdown();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}

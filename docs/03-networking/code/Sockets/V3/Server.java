/**
 * The server implements a protocol
 * 1. It waits for the name of the person
 * 2. Then it expects the date birth
 * 3. It registers the person and returns a (unique) identifier
 */
import java.net.*;
import java.io.*;
import java.util.*;

class ServerTask{
    private Socket client;
    public ServerTask(Socket client){
        this.client = client;
    }

    public Person execute(){
        try{
            ObjectInputStream input_client = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream output_client = new ObjectOutputStream(client.getOutputStream());
            // 1. Receive the name
            String name = (String) input_client.readObject();
            // 2. Receive a date
            Date date = (Date) input_client.readObject();
            // 3. Create the person
            Person P = new Person(name, date);
            // 4. Send to the client the unique identifier
            output_client.writeObject(P.getId());
            this.client.close();
            return P;
        }
        catch(Exception E){
            System.out.println(E);
        }
        return null;

    }
}

public class Server {
    static List<Person> database = new ArrayList<>();

    public static void main(String arg[]) {
        ServerSocket server;
        try {
            server = new ServerSocket(12345);
            System.out.println("Waiting for connections...");
            while(true){
                Socket client = server.accept();
                System.out.println("Connection  [OK] ");
                ServerTask T = new ServerTask(client);
                Person P = T.execute();
                Server.database.add(P);
                System.out.println(Server.database);
            }

        } catch (IOException E) {
            E.printStackTrace();
        }
    }
}

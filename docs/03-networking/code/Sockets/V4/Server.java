/**
 * The server implements a protocol
 * 1. It waits for the name of the person
 * 2. Then it expects the date birth
 * 3. It registers the person and returns a (unique) identifier
 *
 * NOTE: There is a little problem in this code (see Problem.java)
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class ServerTask implements Runnable{
    private Socket client;
    public ServerTask(Socket client){
        this.client = client;
    }

    public void run(){
        try{
            System.out.println("[START] new client.");
            ObjectInputStream input_client = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream output_client = new ObjectOutputStream(client.getOutputStream());
            // 1. Receive the name
            String name = (String) input_client.readObject();
            // 2. Receive a date
            Date date = (Date) input_client.readObject();
            // 3. Create the person
            Person P = new Person(name, date);
            // Simulating a "heavy" task
            Thread.sleep(2000);
            // 4. Send to the client the unique identifier
            output_client.writeObject(P.getId());
            this.client.close();
            Server.database.add(P);
            System.out.println("[CREATED]: " + P);
        }
        catch(Exception E){
            System.out.println(E);
            E.printStackTrace();
        }
    }
}

public class Server {
    // This is problematic 
    static List<Person> database = new ArrayList<>();
    // Solution: Collections.synchronizedList (see Problem.java)
    public static final int NUMPROCS = 5 ;
    public static final int PORT = 12345 ;
    private ServerSocket server;
    private ExecutorService executor;

    public Server() throws IOException{
        this.server = new ServerSocket(Server.PORT);
        this.executor = Executors.newFixedThreadPool(Server.NUMPROCS);
    }

    public void start(){
        try {
            System.out.println("Waiting for connections...");
            while(true){
                this.executor.execute(new ServerTask(server.accept()));
                System.out.println("Connection  [OK] ");
            }

        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    public static void main(String arg[]) {
        Server S = null;
        try{
            S = new Server();
            S.start();
        }
        catch(Exception E){
            System.out.println(E);
            E.printStackTrace();
        }
        finally{
            if(S != null)
                S.executor.shutdown();
        }
    }
}

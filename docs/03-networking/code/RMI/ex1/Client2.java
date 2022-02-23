import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client2 {
    public static void main(String[] args) {
        Random R = new Random(System.currentTimeMillis());

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            PersonService stub = (PersonService) registry.lookup("Server");
            while(true){
                List<Person> L = stub.search("er");
                // Querying the database (via remote method invocation)
                System.out.println(L.size() + " people in the database.");
                Thread.sleep(R.nextInt(200));
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

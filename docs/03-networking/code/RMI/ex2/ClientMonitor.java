import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * A class for testing the listeners (callbacks)
 */

public class ClientMonitor {
    public static void main(String[] args) {
        Random R = new Random(System.currentTimeMillis());

        try {
            Monitor M = new Monitor();
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            PersonService stub = (PersonService) registry.lookup("Server");

            // Adding M to the set of listeners
            stub.addListener(M);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

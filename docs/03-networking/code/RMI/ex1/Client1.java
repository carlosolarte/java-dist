import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.Random;

public class Client1 {
    public static void main(String[] args) {
        Random R = new Random(System.currentTimeMillis());

        try {
            // Service of names
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            // Finding the remote object
            PersonService stub = (PersonService) registry.lookup("Server");
            // Creating (by invoking a remote method) 1000 people
            for(int i=1;i<=1000;i++){
                stub.addPerson("Person-" + R.nextInt(2000),new Date());
                System.out.print(".");
                Thread.sleep(R.nextInt(40));
            }
            System.out.println("");

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

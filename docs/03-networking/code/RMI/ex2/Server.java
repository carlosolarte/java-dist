/**
 * Implementation of the interface and registering the object
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server implements PersonService {
    private List<Person> database = new ArrayList<>();
    private List<PersonMonitor> listeners = new ArrayList<>();

    public Server() {}

    @Override
    public synchronized void addPerson(String name, Date bday){
        System.out.println("["+name+"] added");
        Person P = new Person(name,bday);
        this.database.add(P);
        this.notifyAll(P);
    }

    /**
     * Notify all the listeners that P has been added to the database
     */
    private void notifyAll(Person P){
        for(PersonMonitor listener : listeners){
            try{
                listener.notifyNewPerson(P);
            }
            catch(RemoteException E){
                System.out.println(E);
            }
        }
    }

    @Override
    public synchronized List<Person> search(String name){
        return 
            this.database.stream()
            .filter( p -> p.getName().contains(name))
            .collect(Collectors.toList())
            ;
    }

    @Override
    public synchronized void addListener(PersonMonitor PM){
        listeners.add(PM);
    }

    public static void main(String args[]) {

        try {
            Server server = new Server();
            // Making the object available for remote invocations
            PersonService stub = (PersonService) UnicastRemoteObject.exportObject(server, 12345);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}


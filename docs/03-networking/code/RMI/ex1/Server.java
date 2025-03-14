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
        
    public Server() {}

	@Override
    public synchronized void addPerson(String name, Date bday)throws RemoteException {
        System.out.println("["+name+"] added");
		this.database.add(new Person(name, bday));
	}

    @Override
    public synchronized List<Person> search(String name) throws RemoteException{
        return 
            this.database.stream()
                .filter( p -> p.getName().contains(name))
                .collect(Collectors.toList())
                ;
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


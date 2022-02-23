/**
 * Interface declaring the methods available from remote objects
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface PersonService extends Remote {

        // Adding a new person to the database
        void addPerson(String name, Date bday) throws RemoteException;

        // List the people whose name is name
        List<Person> search(String name) throws RemoteException;

        // Adding a new client willing to be notified when new people is added
        void addListener(PersonMonitor PM) throws RemoteException;
}

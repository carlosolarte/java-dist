/**
 * Interface declaring the methods available from remote obects
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface PersonService extends Remote {
        // Adding a new person to the database
        void addPerson(String name, Date bday) throws RemoteException;
        // List the people whose name is name
        List<Person> search(String name) throws RemoteException;
}

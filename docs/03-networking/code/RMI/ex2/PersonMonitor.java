/**
 * Interface declaring the methods for a client willing to know 
 * when a new person is added to the database (Callbacks)
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface PersonMonitor extends Remote {
        // Notifying when a new person is added
        void notifyNewPerson(Person P) throws RemoteException;
}

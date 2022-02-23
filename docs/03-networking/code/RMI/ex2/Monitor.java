/**
 * Implementation of the interface for the CallBack
 */

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.Serializable ;

public class Monitor extends UnicastRemoteObject implements PersonMonitor {

    public Monitor() throws RemoteException{
    }

    @Override
    public  void notifyNewPerson(Person P){
        System.out.println("["+P.getName()+"] was added");
    }

}


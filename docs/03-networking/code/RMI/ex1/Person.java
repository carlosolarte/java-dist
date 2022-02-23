/**
 * A simple class representing people
 */

import java.util.Date;
import java.io.Serializable ;

// Implements Serializable (since the remote object may send a list of people)
public class Person implements Serializable{

    private String name;
    private Date bday;

    public Person(String name, Date bday){
        this.name= name;
        this.bday = bday;
    }

    public String toString(){
        return this.name + " (" + this.bday + ")"; 
    }

    public String getName(){return this.name;}
    public Date getBDay(){return this.bday;}
}

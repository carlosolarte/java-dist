import java.util.*;
import java.text.*;

public class Person{
    private String name;
    private Date date;
    private UUID id;

    public Person(String name, Date date){
        this.name = name;
        this.date = date;
        this.id = UUID.randomUUID();
    }

    public String getName(){return this.name;}
    public Date getDate(){return this.date;}
    public UUID getId(){return this.id;}
    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "[" + this.id + "]: " + this.name + " (" + dateFormat.format(this.date) + ")";
    }
}


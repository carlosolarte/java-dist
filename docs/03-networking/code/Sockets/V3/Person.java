import java.util.Date;
import java.util.UUID;

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
        return "[" + this.id + "]: " + this.name + " ( " + this.date + ")";
    }
}
        

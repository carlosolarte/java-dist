import java.util.*;
public class Person {
    private String name;
    protected List< String > othernames; 
    public transient String company ;
    private static final String companyExp = new String("unknown");

    public Person(Object o){
        assert o instanceof String ;
        String name = (String) o;
        this.name = (String) o;
    }

    public void hello(){
        System.out.println("Hi " + this.name);
    }

    public static void main(String arg[]){
        Person P = new Person("carlos");
        P.hello();
        System.out.println(Person.companyExp);
        Person P2 = new Person(3); 
    }
}

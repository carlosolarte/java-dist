import java.util.*;

/** A simple interface */
interface Hi{
    public String hi(String name);
}

/** A class implementing the interface */
class English implements Hi{
    public String hi(String name){
        return "Hello " + name + "!";
    }
}


class AnonymousClassTest{
    public static void main(String arg[]){
        // Instantiating Hi with a class implementing the interface
        Hi english = new English();
        // Instantiating Hi with an anonymous class
        Hi french = new Hi(){
            public String hi(String nom){
                return "Bonjour " + nom + "!";
            }};
        // Instantiating using a lambda expression
        Hi spanish = (String nombre) -> "Hola " + nombre + "!";

        List<Hi> l = new ArrayList<>(Arrays.asList(english, french, spanish));
        // Polymorphic calls
        for (Hi h : l)
            System.out.println(h.hi("Java"));

    }
}

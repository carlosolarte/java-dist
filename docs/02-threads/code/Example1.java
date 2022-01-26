/**
 * A simple example showing how to create and run a thread
 */

public class Example1{
    public static void main(String arg[]){
        Thread T = new Thread(); // Instantiating the thread
        System.out.println(T.getState());
        T.start(); // Starting the thread
        System.out.println(T.getState());
        try{
            T.join(); // Wait for this thread to die
            System.out.println(T.getState());
        }
        catch(InterruptedException E){
            System.out.println(E);
        }
    }
}


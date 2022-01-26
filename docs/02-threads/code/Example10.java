/**
 * An example using lambda expression to create tasks
 */
import java.util.concurrent.locks.* ;
public class Example10 {
    public static int sum;
    public static synchronized void inc(){
        Example10.sum += 1;
    }
    public static synchronized void dec(){
        Example10.sum -= 1;
    }
    public static void main(String arg[]){

        Runnable inc = () ->{
            for(int i=1;i<10000;i++) Example10.inc();
        };
        Runnable dec = () ->{
            for(int i=1;i<10000;i++) Example10.dec();
        };


        Thread T1 = new Thread(inc);
        Thread T2 = new Thread(dec);

        T1.start();
        T2.start();
        try{
            T1.join();
            T2.join();
        }
        catch(Exception E){
            System.out.println(E);
        }
        System.out.println("Sum: " + Example10.sum);
    }

}

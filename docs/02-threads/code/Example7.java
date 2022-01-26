/**
 * An example using explicit locks (instead of synchornized methods)
 */
import java.util.concurrent.locks.* ;
class Data{
    public static int sum = 0 ;
    public static final Lock L = new ReentrantLock();
    public static void update(int step){
        try{
            L.lock(); // Acquire the lock
            Data.sum += step; // Critical section
            L.unlock(); // Release the lock
           }
        catch(Exception E){
            System.out.println(E);
        }
    }
}
public class Example7 extends Thread {

    public int step, n;
    public Example7(int step, int n){
        this.step = step;
        this.n = n;
    }

    @Override
    public void run(){
        for(int i=1;i<=this.n;i++){
            Data.update(this.step);
        }
    }

    public static void main(String arg[]){
        Example7 E1 = new Example7(1,10000);
        Example7 E2 = new Example7(-1,10000);
        E1.start();
        E2.start();
        try{
            E1.join();
            E2.join();
        }
        catch(Exception E){
            System.out.println(E);
        }
        System.out.println("Sum: " + Data.sum);
    }

}

/**
 * An example using explicit locks (instead of synchornized methods)
 */
import java.util.concurrent.locks.* ;
class Data{
    public static int sum = 0 ;
    public static final Lock L = new ReentrantLock();
    public static void update(int step){
        try{
            L.lock();
            System.out.println(Thread.currentThread() + " enter.");
            Data.sum += step;
            Thread.sleep(2000);
            System.out.println(Thread.currentThread() + " exit.");
            L.unlock();
        }
        catch(Exception E){
            System.out.println(E);
        }
    }
}
public class Example8 extends Thread {

    public int step, n;
    public Example8(int step, int n){
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
        Example8 E1 = new Example8(1,3);
        Example8 E2 = new Example8(-1,3);
        E1.setName("E1");
        E2.setName("E2");
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

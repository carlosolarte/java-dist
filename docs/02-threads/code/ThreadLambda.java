/**
 * An example using lambda expression to create tasks
 * Note that inc is not synchronized and then, the final
 * result is not necessarily 0!
 */
import java.util.concurrent.locks.* ;

class Data{
    int sum = 0;
    public void inc(int x){
        this.sum += x;
    }
    public synchronized void dec(int x){
        this.sum -= x;
    }

    public int get(){
        return this.sum;
    }
}

public class ThreadLambda {
    public static void main(String arg[]){
        Data d = new Data();
        Runnable inc = () ->{
            for(int i=1;i<1000;i++) d.inc(1);
        };
        Runnable dec = () ->{
            for(int i=1;i<1000;i++) d.dec(1);
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
        System.out.println("Sum: " + d.get());
    }
}

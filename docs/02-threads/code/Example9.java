/**
 * Synchronized statetements
 */

import java.util.concurrent.locks.* ;
class Data{
    private int sum = 0 ;
    public int getSum(){return this.sum;}
    public void setSum(int sum){this.sum = sum;}
}
public class Example9 extends Thread {
    public int step, n;
    public Data D;
    public Example9(int step, int n, Data D){
        this.step = step;
        this.n = n;
        this.D = D ;
    }

    @Override
    public void run(){
        for(int i=1;i<=this.n;i++){
            // The lock on this.D must be acquired before
            // executing the synchronized block
            synchronized(this.D){
                // At this point, no other thread can have
                // a lock on D
                int temp = this.D.getSum();
                D.setSum(temp + this.step);
            } // At this point the lock on D is released
        }
    }

    public static void main(String arg[]){
        Data D = new Data();
        Example9 E1 = new Example9(1,10000,D);
        Example9 E2 = new Example9(-1,10000,D);
        E1.start();
        E2.start();
        try{
            E1.join();
            E2.join();
        }
        catch(Exception E){
            System.out.println(E);
        }
        System.out.println("Sum: " + D.getSum());
    }

}

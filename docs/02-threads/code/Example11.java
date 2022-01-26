/**
 * Note the use of T1.join to "wait" for the end of the computation
 * doing T1.start() ; print(T1.getResult()) does NOT work
 */
import java.util.concurrent.locks.* ;
public class Example11 extends Thread{
    private double result;

    public double getResult(){
        return this.result;
    }

    public void run(){
        try{
            // Long computation computing PI
            Thread.sleep(3000);
            System.out.println("Ready!");
            this.result = Math.PI ;
        }
        catch (Exception E){
            System.out.println(E);
        }
    }
    public static void main(String arg[]){

        Example11 T1 = new Example11();
        T1.start();
        try{
            // Waiting for the end of the computation
            T1.join();
            System.out.println(T1.getResult());
        }
        catch(Exception E){ }
    }
}

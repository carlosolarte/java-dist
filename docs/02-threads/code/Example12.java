/*
 * Example of a callback, informing the value of the computation 
 */
import java.util.concurrent.locks.* ;

class ComputingPI extends Thread{
    public void run(){
        try{
            // Long computation computing PI
            Thread.sleep(3000);
            System.out.println("Ready!");
            // Callback, informing the result of the computation
            Example12.newResult(Math.PI);
        }
        catch (Exception E){
            System.out.println(E);
        }
    }
}
public class Example12 {

    public static void newResult(double R){
        System.out.println("Result: " + R);
    }

    public static void main(String arg[]){

        ComputingPI T1 = new ComputingPI();
        T1.start();
    }

}

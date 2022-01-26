/*
 * Example of a callback, informing the value of the computation 
 * In this case, we are not using a static method. 
 */
import java.util.concurrent.locks.* ;

class ComputingPI extends Thread{

    private Example13 E; // Object used to communicate the result
    public ComputingPI(Example13 E){
        this.E = E ;
    }
    public void run(){
        try{
            // Long computation computing PI
            Thread.sleep(3000);
            System.out.println("Ready!");
            this.E.newResult(Math.PI);
        }
        catch (Exception E){
            System.out.println(E);
        }
    }
}
public class Example13 {

    public void newResult(double R){
        System.out.println("Result: " + R);
    }
    
    public static void main(String arg[]){
        Example13 E = new Example13();
        ComputingPI T1 = new ComputingPI(E);
        T1.start();
    }
}

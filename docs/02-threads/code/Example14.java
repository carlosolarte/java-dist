/**
 * An example using the interface Callable. 
 * A future is created and then, we can "wait" for the result of the
 * asynchronous task
 */
import java.util.concurrent.*;

class ComputingPI implements Callable<Double>{

    /**
     * This method implements the asynchronous task returning a value
     */
    @Override
    public Double call(){
        try{
            // Long computation computing PI
            Thread.sleep(3000);
            System.out.println("Ready!");
        }
        catch (Exception E){
            System.out.println(E);
        }
        return Math.PI;
    }
}
public class Example14 {

    public static void main(String arg[]){
        try{
            ExecutorService service = 
                Executors.newFixedThreadPool(2);
            Future<Double> value = 
                service.submit(new ComputingPI());
            System.out.println("Invoking get on the future");
            System.out.println(value.get());
            System.out.println("After get");
            service.shutdown();
        }
        catch(Exception E){
            System.out.println(E);
        }
    }

}

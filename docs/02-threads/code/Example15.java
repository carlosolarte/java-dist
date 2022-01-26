/**
 * Callables and submitting several tasks
 */
import java.util.concurrent.*;

class ComputingSum implements Callable<Double>{
    private double[] data ;
    int ini, end;

    public ComputingSum(double[] data, int ini, int end){
        this.data = data ;
        this.ini  = ini;
        this.end  = end;
    }

    @Override 
    public Double call(){
        double result = 0;
        try{
            System.out.println("Starting!");
            // Long computation of sum
            for(int i=ini; i < end ; i++){
                result += this.data[i];
                Thread.sleep(500);
            }
            System.out.println("Ready!");
        }
        catch (Exception E){
            System.out.println(E);
        }
        return result;
    }
}
public class Example15 {

    public static void main(String arg[]){
        double data[] = {1,2,3,4};
        long start = System.currentTimeMillis();
        try{
            // An executor with 2 threads (try with only one!)
            ExecutorService service = 
              Executors.newFixedThreadPool(2);
            // C1 computes on the first half of the array
            ComputingSum C1 = new ComputingSum(data,0,2);
            // C2 computes on the second half of the array
            ComputingSum C2 = new ComputingSum(data,2,4);

            // Submitting the tasks to the executor
            Future<Double> v1 = service.submit(C1);
            Future<Double> v2 = service.submit(C2);

            // Get waits for the result to be ready
            System.out.println(v1.get() + v2.get());

            // Terminating the executor
            service.shutdown();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println("Time: " + timeElapsed);
        }
        catch(Exception E){
            System.out.println(E);
        }
    }

}

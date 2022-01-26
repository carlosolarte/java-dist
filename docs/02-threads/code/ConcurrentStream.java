/**
 * An example of processing in parallel a stream
 */
import java.util.stream.*;
import java.util.*;
class ConcurrentStream{
    public static int compute(int i){
        try{
            // A "long" operation that takes 1 second
            Thread.sleep(1000);
        }
        catch(InterruptedException E){}
        return i*2;
    }

    public static void main(String arg[]){

        long start = System.currentTimeMillis();
        int data[] = new int[] {1,2,3,4,5,6,7,8,9,10};
        int sum = Arrays.stream(data)
            . parallel() // Processing the stream in parallel
            . map( ConcurrentStream::compute)
            . sum()
            ;
        System.out.println(sum);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Time: "  + timeElapsed);
    }
}

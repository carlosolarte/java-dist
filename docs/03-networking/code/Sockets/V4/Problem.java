/**
 * A little code showing the problem of not synchronizing the ArrayList in the
 * V4 example 
 */

import java.util.*;
import java.util.stream.*;

class Task implements Runnable{
    public static int NELEM = 1000;
    private List<Integer> l;
    public Task(List<Integer> l){
        this.l = l;
    }

    public void run(){
        Random random = new Random();
        IntStream str = random.ints(0, 1000);
        str
            .limit(NELEM)
            .forEach( (i) -> l.add(i));

    }
}

public class Problem{
    public static final int NTHREADS = 20;
    public static void main(String arg[]){
        // Problem
        List<Integer> l = new ArrayList<Integer>();
        // Solution 
        //List<Integer> l = Collections.synchronizedList(new ArrayList<>());
        Thread[] threads = new Thread[NTHREADS];
        for (int i=0;i<NTHREADS;i++)
            threads[i] = new Thread(new Task(l));
        for (Thread t : threads)
            t.start();

        for (Thread t : threads){
            try{
                t.join();
            }
            catch(Exception E){
            }
        }
        System.out.println(l.size());
    }
}

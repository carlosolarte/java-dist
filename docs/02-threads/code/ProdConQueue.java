/**
 * Using BlockingQueue to implement a producer/consumer system
 */
import java.util.Random;
import java.util.concurrent.*;

class Producer implements Runnable{
    private BlockingQueue<String> Q;
    private int num_messages = 0;
    public Producer(BlockingQueue<String> Q){
        this.Q = Q ;
    }

    // Adding new element to the structure and then, sleeping for i seconds
    public void produce(int i){
        try{
            // This operation blocks when there is no more space in the structure
        	this.Q.put("Message " + (this.num_messages++));
			System.out.println("Currently " + Q.size() + " messages in the Queue");

            Thread.sleep(i);
        }
        catch(InterruptedException E){}
    }

    public void run(){
        int n=0;
        // Infinite sequence of random numbers
        Random R = new Random(System.currentTimeMillis());
        R.ints(50,1000)
        	.forEach( i -> produce(i));
    }
}

class Consumer implements Runnable{
    private BlockingQueue<String> Q;
    public Consumer(BlockingQueue<String> Q){
        this.Q = Q ;
    }

    public void run(){
        while(true){
			try{
                // This operation blocks until new elements are available
				String m = this.Q.take();
				System.out.println("[rec " + this + "]: " + m);
				Thread.sleep(4500);
			}
			catch(InterruptedException E){
				System.out.println(E);
			}
        }
    }
}

class ProdConQueue{
    public static final int nConsumers = 5;
    public static void main(String arg[]){
        BlockingQueue<String> Q = new ArrayBlockingQueue<>(5);
        for(int i=0;i<ProdConQueue.nConsumers;i++)
            (new Thread(new Consumer(Q))).start();
        
        (new Thread(new Producer(Q))).start();
    }
}



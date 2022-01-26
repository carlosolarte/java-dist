/**
 * Race conditions: uncontrolled access to the shared resource Example5.sum
 */
public class Example5 extends Thread {
    public static int sum = 0 ;
    public int step, n;

    public Example5(int step, int n){
        this.step = step;
        this.n = n;
    }

    @Override
    public void run(){
        // perform n times the increment/decrement of Example5.sum
        for(int i=1;i<=this.n;i++){
            Example5.sum += this.step;
        }
    }

    public static void main(String arg[]){
        Example5 E1 = new Example5(1,10000);
        Example5 E2 = new Example5(-1,10000);
        E1.start();
        E2.start();
        try{
            // Waiting for termination of both threads
            E1.join();
            E2.join();
        }
        catch(Exception E){
            System.out.println(E);
        }
        System.out.println("Sum: " + Example5.sum);
    }

}

/**
 * Implementing the interface Runnable
 */

public class Example3 implements Runnable {
    private String msg;
    private int num, delay;
    public Example3(String msg,int num, int delay){
        this.msg = msg;
        this.num = num;
        this.delay = delay;
    }

    /**
     * Method to be overridden to implement the intended task
     */
    @Override
    public void run(){
        for(int i=1;i<=this.num;i++){
            System.out.println(i + ". " + this.msg  );
            try{
                Thread.sleep(this.delay);
            }
            catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
    }

    public static void main(String arg[]){
        Example3 EH = new Example3("Hello", 10,1000);
        Example3 EW = new Example3("World", 10, 300);
        // New threads are created whose parameter is an instance of Runnable
        Thread T1 = new Thread(EH);
        Thread T2 = new Thread(EW);
        T1.start();
        T2.start();
    }
}

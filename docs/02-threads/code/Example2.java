/**
 * A more concrete example extending the class Thread
 */
public class Example2 extends Thread {
    private String msg;
    private int num, delay;
    public Example2(String msg,int num, int delay){
        this.msg = msg;
        this.num = num;
        this.delay = delay;
    }

    /**
     * This method needs to be overriding to implement the intended task
     */
    @Override
    public void run(){
        for(int i=1;i<=this.num;i++){
            System.out.println(i + ". " + this.msg  );
            try{
                // Suspending the execution
                Thread.sleep(this.delay);
            }
            catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
    }

    public static void main(String arg[]){
        Example2 EH = new Example2("Hello", 10,1000);
        Example2 EW = new Example2("World", 10, 1000);
        EH.start();
        EW.start();
    }
}

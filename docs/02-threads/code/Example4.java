/**
 * Names and priorities in threads
 */
public class Example4 extends Thread {
    private String msg;
    private int num, delay;
    public Example4(String msg,int num, int delay){
        this.msg = msg;
        this.num = num;
        this.delay = delay;
    }

    @Override
    public void run(){
        for(int i=1;i<=this.num;i++){
            // this.toString returns the name, priority and the thread group
            System.out.println(this + ". " +  this.msg);
            try{
                Thread.sleep(this.delay);
            }
            catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
    }

    public static void main(String arg[]){
        Example4 E1 = new Example4("Hello", 10,500);
        Example4 E2 = new Example4("Hello", 10,500);
        // Changing the name of the thread
        E1.setName("My-thread ");
        E1.start();
        E2.start();
    }

}

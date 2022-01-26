/**
 * First example of Synchronized methods
 */
class Data{
    public static int sum = 0 ;

    /**
     * Only one thread can execute this method at the same time
     * (no interleaving during execution)
     */
    public static synchronized 
        void update(int step){
        Data.sum += step;
    }
}
public class Example6 extends Thread {

    public int step, n;
    public Example6(int step, int n){
        this.step = step;
        this.n = n;
    }

    @Override
    public void run(){
        for(int i=1;i<=this.n;i++){
            Data.update(this.step);
        }
    }

    public static void main(String arg[]){
        Example6 E1 = new Example6(1,10000);
        Example6 E2 = new Example6(-1,10000);
        E1.start();
        E2.start();
        try{
            E1.join();
            E2.join();
        }
        catch(Exception E){
            System.out.println(E);
        }
        System.out.println("Sum: " + Data.sum);
    }

}

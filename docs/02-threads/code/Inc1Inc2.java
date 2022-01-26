/**
 * An example of two independent methods that not need
 * to be synchronized
 */
public class Inc1Inc2 {
    private long c1 = 0;
    private long c2 = 0;
    // Two objects used for synchronization purposes
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        // Before executing c1++, a lock on lock1 must be acquired 
        synchronized(lock1) { 
            try{
                System.out.println("Enter C1");
                c1++; 
                Thread.sleep(1000);
                System.out.println("Exit C1");
            }
            catch(InterruptedException E){}
        }
    }

    public String toString(){
        return "c1= " + this.c1 + ", c2= " + this.c2;
    }

    public void inc2() {
        // Before executing c2++, a lock on lock1 must be acquired 
        synchronized(lock2) { 
            try{
                System.out.println("Enter C2");
                c2++; 
                Thread.sleep(1000);
                System.out.println("Exit C2");
            }
            catch(InterruptedException E){}
        }
    }

    public static void main(String arg[]){
        Inc1Inc2 D = new Inc1Inc2();

        Thread T1 = new Thread(){
            public void run(){
                for (int i=1;i<=3;i++) D.inc1();
            }
        };
        Thread T2 = new Thread(){
            public void run(){
                for (int i=1;i<=3;i++) D.inc2();
            }
        };

        T1.start();
        T2.start();
        try{
            T1.join();
            T2.join();
        }
        catch(InterruptedException E){}
        System.out.println(D);
    }
}

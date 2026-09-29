// A simple example of a semaphore 
import java.util.concurrent.Semaphore;
class Resource{
    public void use(){
        try{
            System.out.println("In use");
            Thread.sleep(2000);
        }
        catch(InterruptedException E){}
    }
}

class SemTest{
    private Semaphore semaphore;
    private Resource R;

    public SemTest(int nClients){
        this.semaphore = new Semaphore(nClients);
        R = new Resource();
    }

    public void useResource(){
        try {
            semaphore.acquire();
            R.use();
        } 
        catch(InterruptedException E){}
        finally {
            semaphore.release();
        }
    }

    public static void main(){
        // Only 3 at a time 
        SemTest control = new SemTest(3);
        for(int i=1;i<=30;i++){
            (new Thread(() -> control.useResource())).start();
        }

    }

}

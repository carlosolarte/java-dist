@Run
public class Task1 {
    public Task1(){}

    public void run(){
        while(true){
            try{
                Thread.sleep(2000);
                System.out.println("Task1");
            }
            catch (Exception E){
            }
        }
    }
}

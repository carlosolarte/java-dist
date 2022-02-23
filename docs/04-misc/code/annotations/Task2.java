@Run(method="execute")
public class Task2 {
    public Task2(){}

    public void execute(){
        while(true){
            try{
                Thread.sleep(2000);
                System.out.println("Task2");
            }
            catch (Exception E){
            }
        }
    }
}

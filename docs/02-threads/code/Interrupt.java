/**
 * An example of interrupting a thread
 */

class Signal extends Thread{
    private String m;
    public Signal(String m){ this.m = m;}

    public void run(){
        try{
            // Infinite loop printing the same message
            while(true){
                System.out.println(this.m);
                Thread.sleep(1000);
                }
            }
        catch(Exception E){
            System.out.println("Thread interrupted...");
            System.out.println(E);
        }
    }
}
class Interrupt{
        
    public static void main(String arg[]){
        Signal S = new Signal("beep");
        S.start();
        try{
            // After 4 seconds (approximately), the thread Signal 
            // is interrupted
            Thread.sleep(4000);
            S.interrupt();
            }
        catch(Exception E){
            System.out.println(E);
        }

    }
}

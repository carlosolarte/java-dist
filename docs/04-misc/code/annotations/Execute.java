import java.util.*;
import java.io.*;
import java.lang.reflect.* ;

public class Execute{
    /**
     * Returns all the classes available in the current with annotation @Run
     */
    public static List<Class> allRunClasses(){
        List<Class> L = new ArrayList<>() ;
        File curDir = new File("./");
        for (File F : curDir.listFiles()){
            try{
                if (F.isFile()){
                    String fname = F.getName();
                    if (fname.endsWith(".class")){
                        String className = fname.replace(".class", "");
                        Class C = Class.forName(className);

                        if(C.getAnnotation(Run.class) != null)
                            L.add(C);
                    }
                }
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return L;
    }

    public static void runAll(){
        List<Class> L = Execute.allRunClasses();
        for (Class C : L){
            try{
                // Creating an instance of the class 
                Object O = C.newInstance();
                Run runClass = (Run) C.getAnnotation(Run.class);
                // Obtaining the method to be run
                Method M = C.getMethod(runClass.method());
                // Executing the method in a thread
                Thread T = new Thread(){
                    @Override
                    public void run(){
                        try{
                            M.invoke(O);
                        }
                        catch(Exception E){
                            E.printStackTrace();
                        }
                    }
                };
                T.start();
            }
            catch(Exception E){
                E.printStackTrace();
            }
        }

    }
    public static void main(String arg[]){
        Execute.runAll();
    }
}

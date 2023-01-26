import java.util.*;

/** An example using reduce */

public class ReduceExample{
    public static void main(String arg[]){
        List<Integer> l1 = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
        List<Integer> l2 = new ArrayList<>();

        Optional<Integer> res =
                l1.stream()
                 .reduce( (x,y) -> x+y) ;

        System.out.println(res);
        System.out.println(res.get());

        res = l2.stream()
                 .reduce( (x,y) -> x+y) ;
        System.out.println(res);
    }
}

import java.util.function.*;
import java.util.*;
import java.util.stream.*;


class Grade{
    private String name;
    private double grade ;

    public Grade(String name, double grade){
        this.name = name;
        this.grade = grade ;
    }

    public double getGrade(){
        return this.grade;
    }

    public String toString(){
        return this.name  + ": " + this.grade;
    }

}

class GradeStream{
    public static void main (String arg[]){
        List<Grade> L = new ArrayList<>();
        L.add(new Grade("one"  , 4.5 ));
        L.add(new Grade("two"  , 8.5 ));
        L.add(new Grade("three", 9.2 ));
        L.add(new Grade("four" , 10.0));
        L.add(new Grade("five" , 7.4 ));
        L.add(new Grade("six"  , 3.1 ));
        L.add(new Grade("seven", 9.1 ));

        Stream<Grade> S =
             L.stream()
              .filter((Grade G) -> G.getGrade() >= 5.0);
        // toArray materializes the stream!
        System.out.println(Arrays.toString(S.toArray()));
    
        L.stream()
              .filter((Grade G) -> G.getGrade() >= 5.0)
              .forEach( (Grade G) -> System.out.println(G))
              ;

        L.stream()
            .map((Grade G) -> G.getGrade())
            .forEach( (Double n) -> System.out.println(n))
            ;

        OptionalDouble R = 
            L.stream()
            .mapToDouble((Grade G) -> G.getGrade())
            .max()
            ;
        System.out.println(R);
        */
             

    }
}



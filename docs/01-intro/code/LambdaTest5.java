import java.util.*;

interface I{
    public int temp(){
     return 3;
     }
}

class LambdaTest5{
    public static void main(String arg[]){
        String[] L = new String[] {"car", "house", "building", "apple", "fruit"};
        Arrays.sort(L, (s1,s2) -> s1.compareTo( s2));
        System.out.println(Arrays.toString(L));
        Arrays.sort(L, (s1,s2) -> s2.compareTo( s1));
        System.out.println(Arrays.toString(L));
        Arrays.sort(L, (s1,s2) -> s1.length() - s2.length()) ;
        System.out.println(Arrays.toString(L));

        Comparator<String> C = (s1, s2) -> s1.length() - s2.length();
        Arrays.sort(L, C.reversed());
        System.out.println(Arrays.toString(L));

    }
}





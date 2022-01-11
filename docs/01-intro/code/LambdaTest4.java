import java.util.*;

class LambdaTest4{
    public static void main(String arg[]){
        String[] L = new String[] {"car", "house", "building", "apple", "fruit"};
        Comparator<String> CLength = new Comparator <String> (){
            public int compare(String s1, String s2){
                return s1.length() - s2.length();
            }
        };

        Arrays.sort(L, CLength);
        System.out.println(Arrays.toString(L));
    }
}





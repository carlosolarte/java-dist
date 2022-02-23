/**
 * An example of a functional interface
 */

import java.util.Arrays;

@FunctionalInterface
interface IntConvert{
    String convert(Integer I);
}


public class FInter{
    public static String[] convert(int [] input, IntConvert C){
        String [] res = new String[input.length];
        for(int i=0;i<res.length;i++)
            res[i] = C.convert(input[i]);
        return res;
    }
    public static void main(String arg[]){

        // Expects values in base 10 an returns values in binary
        IntConvert toBin = (x) -> Integer.toString(x,2);
        IntConvert toHex = (x) -> Integer.toString(x,16);

        int data[] = {1,5,23,47};

        System.out.println(Arrays.toString(FInter.convert(data, toBin)));
        System.out.println(Arrays.toString(FInter.convert(data, toHex)));
    }
}

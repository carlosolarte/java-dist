/**
 * An example of a functional interface
 */

import java.util.Arrays;
import java.util.stream.*;

@FunctionalInterface
interface IntConvert{
    String convert(Integer I);
}


public class FInter{
    public static Stream<String> convert(Stream<Integer> input, IntConvert C){
        return input.map( C::convert);

    }
    public static void main(String arg[]){

        // Expects values in base 10 an returns values in binary
        IntConvert toBin = (x) -> Integer.toString(x,2);
        IntConvert toHex = (x) -> Integer.toString(x,16);

        Integer data[] = {1,5,23,47};

        convert(Arrays.stream(data), toBin).
            forEach( System.out::println);
        convert(Arrays.stream(data), toHex).
            forEach( System.out::println);

    }
}

interface Operator{
        public int op(int a, int b);
}

class LambdaTest3{
    public static void main(String arg[]){
        Operator Plus = (int a, int b) -> a + b ;
        Operator Minus = (int a, int b) -> a - b ;

        System.out.println(Plus.op(1,2));
        System.out.println(Minus.op(1,2));

    }
}





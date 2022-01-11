interface Operator{
        public int op(int a, int b);
}

class Plus implements Operator{
    public int op(int a, int b){
        return a+b;
    }
}

class LambdaTest1{
    public static void main(String arg[]){
        Plus P = new Plus();
        System.out.println(P.op(2,3));
    }
}





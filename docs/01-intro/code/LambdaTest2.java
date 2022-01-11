interface Operator{
        public int op(int a, int b);
}

class LambdaTest2{
    public static void main(String arg[]){
        Operator Plus = new Operator(){
            public int op(int a, int b){
                return a + b;
            }
        };
        System.out.println(Plus.op(2,3));
    }
}





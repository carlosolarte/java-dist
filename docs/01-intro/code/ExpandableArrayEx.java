class BadIndex extends Exception{ 
    public BadIndex(int i){
        super("Bad index: " + i);
    }
}

class ExpandableArrayEx {
    private Object[] data; 
    private int size = 0;
    public static final int EXT = 10; 

    public ExpandableArrayEx(int cap) {
        data = new Object[cap];
    } 

    public int size() { 
        return this.size;
    }

    public Object get(int i) throws BadIndex {
        if (i<0 || i>= size) 
            throw new BadIndex(i);
        return this.data[i];
    }

    public void add(Object x) {
        if (size == data.length) {
            Object[] olddata = this.data;
            this.data = new Object[size + ExpandableArrayEx.EXT];
            System.arraycopy(olddata, 0, this.data, 0, olddata.length);
      }
      this.data[this.size++] = x;
    }

    public static void main(String arg[]){
        ExpandableArrayEx A = new ExpandableArrayEx(5);
        try{
            A.get(0);
            }
        catch(BadIndex E){
            System.out.println(E);
        }
    }
}

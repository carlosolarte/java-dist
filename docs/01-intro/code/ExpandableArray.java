import java.util.NoSuchElementException;

class ExpandableArray {
    private Object[] data; 
    private int size = 0;
    public static final int EXT = 10; 

    public ExpandableArray(int cap) {
        data = new Object[cap];
    } 

    public int size() { 
        return this.size;
    }

    public Object get(int i) throws NoSuchElementException {
        if (i<0 || i>= size) 
            throw new NoSuchElementException(); 
        return this.data[i];
    }

    public void add(Object x) {
        if (size == data.length) {
            Object[] olddata = this.data;
            this.data = new Object[size + ExpandableArray.EXT];
            System.arraycopy(olddata, 0, this.data, 0, olddata.length);
      }
      this.data[this.size++] = x;
    }

    public static void main(String arg[]){
        ExpandableArray A = new ExpandableArray(5);
        for(int i=0;i<10;i++)
            A.add(i);

        for(int i=0;i<A.size();i++)
            System.out.println(A.get(i));

    }
}

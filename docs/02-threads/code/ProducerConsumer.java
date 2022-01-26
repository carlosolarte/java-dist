/**
 * A producer/consumer system with a buffer of fixed size
 * The producer fills (completely) the buffer and, after that, the client
 * consumes all the elements
 */

import java.util.*;

class Buffer{
    public final static int size = 4;
    private String [] data;
    private int counter = 0;

    public Buffer(){
        this.data = new String[Buffer.size];
    }
    public boolean isEmpty(){ return this.counter==0;}
    public boolean isFull(){ return  this.counter==Buffer.size;}

    public synchronized void add(String S)
            throws ArrayIndexOutOfBoundsException{
            this.data[this.counter++] = S;
    }

    public synchronized String get()
            throws ArrayIndexOutOfBoundsException{
            return this.data[--this.counter];
    }
}

class Producer implements Runnable{
    private Buffer B;
    public Producer(Buffer B){ this.B = B;}

    public void run(){
        int n=0;
        while(true){
            // A lock on B is needed
            synchronized(this.B){
                while(! B.isEmpty()){
                    // Wait until being notified 
                    try{ B.wait(); }
                    catch(InterruptedException E){
                        System.out.println(E);
                    }
                }
                // Add new elements to the buffer
                for(int i=1;i<=Buffer.size;i++){
                    n++;
                    System.out.println("[sending] Message " + n);
                    this.B.add("Message " + n);
                    try{Thread.sleep(600);} catch (InterruptedException E){}
                }
                // Notify to all the Threads that something has happened
                B.notifyAll();
            }
        }
    }
}

class Consumer implements Runnable{
    private Buffer B;
    public Consumer(Buffer B){ this.B = B;}

    public void run(){
        while(true){
            // A lock on B is needed
            synchronized(B){
                while(! B.isFull()){
                    try{
                        // Waiting until B is full
                        B.wait();
                    }
                    catch(InterruptedException E){
                        System.out.println(E);
                    }
                }
                for(int i=1;i<Buffer.size;i++){
                    System.out.println("[receive] " + B.get());
                    try{Thread.sleep(400);} catch (InterruptedException E){}
                }
                // Notify to all the Threads that something has happened
                B.notifyAll();
            }
        }
    }
}

class ProducerConsumer{

    public static void main(String arg[]){
        Buffer B = new Buffer();
        Producer P = new Producer(B);
        Consumer C = new Consumer(B);
        (new Thread(P)).start();
        (new Thread(C)).start();
    }
}

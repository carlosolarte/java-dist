/**
 * This implementation is wrong! It might be the case that two philosophers
 * gran the same fork (thus throwing a StateException
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

enum STATE_PHILO {THINKING, HUNGRY , EATING;}
enum STATE_FORK {FREE, INUSE;}

class StateException extends Exception{
    public StateException(){
        super("Non valid operation in the current state");
    }
}

class Fork {
    private STATE_FORK state; // Current state

    public Fork(int ID){
        this.state = STATE_FORK.FREE ;
    }

    // Synchronizing the take/release methods is NOT enough!
    public synchronized void take() throws StateException {
        if (this.state == STATE_FORK.INUSE) 
            throw new StateException();

        this.state = STATE_FORK.INUSE ;
    }
    public synchronized void release() throws StateException{
        if (this.state == STATE_FORK.FREE) 
            throw new StateException();

        this.state = STATE_FORK.FREE ;
    }

    public synchronized boolean isFree(){
        return this.state == STATE_FORK.FREE;
    }

    public String toString(){
        return this.state == STATE_FORK.FREE ? "Y" : "o";
    }
}

class Philosopher extends Thread{
    private STATE_PHILO state = STATE_PHILO.THINKING;
    private int ID;
    private Random RGenerator = new Random(System.currentTimeMillis());
    private Fork right, left ;

    public Philosopher(int ID, Fork right, Fork left){
        this.ID = ID;
        this.right = right;
        this.left = left;
    }

    public void run(){
        while(true){
            try{
                think(); hungry(); eat();
            }
            catch(Exception E){
                System.out.println(E);
            }
        }
    }

    private void think() throws InterruptedException{
        this.state = STATE_PHILO.THINKING;
        while(!this.RGenerator.nextBoolean()){
            Thread.sleep(this.RGenerator.nextInt(5000));
        }
    }

    private void hungry() throws StateException, InterruptedException{
        this.state = STATE_PHILO.HUNGRY;
        right.take();
        left.take();
    }
    private void eat() throws StateException, InterruptedException{
        this.state = STATE_PHILO.EATING;
        while(!this.RGenerator.nextBoolean()){
            Thread.sleep(this.RGenerator.nextInt(5000));
        }
        right.release();
        left.release();
    }

    public String toString(){
        switch (this.state){
            case THINKING : return "~";
            case EATING : return "E";
        }
        return "H";
    }
}

public class DP1{
    public static void main(String arg[]){
        int N = 5; // Number of agents
        Fork forks[] = new Fork[N];
        Philosopher philos[] = new Philosopher[N];

        for (int i=0;i<N;i++)
            forks[i] = new Fork(i);

        for (int i=0;i<N;i++){
            philos[i] = new Philosopher(i,forks[i], forks[(i+1)%N]);
            philos[i].start();
        }

        // Each second prints the state of the system
        try{
            while(true){
                System.out.print(Arrays.toString(philos) + " --- ");
                System.out.println(Arrays.toString(forks) );
                Thread.sleep(1000);
            }
        }
        catch(Exception E){
            System.out.println(E);
            E.printStackTrace();
        }
    }
}

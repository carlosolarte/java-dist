/**
 * Implementation of Dinning Philosophers
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

    public void take() throws StateException {
        if (this.state == STATE_FORK.INUSE) 
            throw new StateException();

        this.state = STATE_FORK.INUSE ;
    }
    public void release() throws StateException{
        if (this.state == STATE_FORK.FREE) 
            throw new StateException();

        this.state = STATE_FORK.FREE ;
    }

    public boolean isFree(){
        return this.state == STATE_FORK.FREE;
    }

    public String toString(){
        return this.state == STATE_FORK.FREE ? "Y" : "o";
    }
}

class Philosopher extends Thread{
    private STATE_PHILO state;
    private int ID;
    private Random RGenerator;
    private Fork right, left ;
    private Condition control;
    private Lock lock ;

    public Philosopher(int ID, Fork right, Fork left, Lock lock, Condition control){
        this.ID = ID;
        this.state = STATE_PHILO.THINKING;
        this.RGenerator = new Random(System.currentTimeMillis());
        this.right = right;
        this.left = left;
        this.control = control;
        this.lock = lock ;
    }

    private void think() throws InterruptedException{
        this.state = STATE_PHILO.THINKING;
        while(!this.RGenerator.nextBoolean()){
            Thread.sleep(this.RGenerator.nextInt(5000));
        }
    }
    private void hungry() throws StateException, InterruptedException{
        this.state = STATE_PHILO.HUNGRY;
        this.lock.lock();
        while(!right.isFree() || !left.isFree()){
            this.control.await();
        }
        // At this point, it is safe to grab both forks
        right.take();
        left.take();
        this.lock.unlock();
    }
    private void eat() throws StateException, InterruptedException{
        this.state = STATE_PHILO.EATING;
        while(!this.RGenerator.nextBoolean()){
            Thread.sleep(this.RGenerator.nextInt(5000));
        }

        this.lock.lock();
            right.release();
            left.release();
            this.control.signalAll();
        this.lock.unlock();
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

    public String toString(){
        switch (this.state){
            case THINKING : return "~";
            case EATING : return "E";
        }
        return "H";
    }
}

public class DP3{
    public static void main(String arg[]){
        Lock lock = new ReentrantLock();
        Condition control = lock.newCondition();
        int N = 5; // Number of agents
        Fork forks[] = new Fork[N];
        Philosopher philos[] = new Philosopher[N];

        for (int i=0;i<N;i++)
            forks[i] = new Fork(i);

        for (int i=0;i<N;i++){
            philos[i] = new Philosopher(i,forks[i], forks[(i+1)%N], lock, control);
            philos[i].start();
        }

        // Each second prints the state of the of the system
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

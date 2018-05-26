package console.Algorithms;

import java.time.LocalDateTime;

/*
    Description :   A process is an instance of a program running in a computer. It is close in meaning to task .
 */

public class Process implements Runnable{

    private String name; // Process name
    private int arrival; // Arrival time
    private int cycleTime; // Cycles time
    private int  wait; // waiting time
    private int service; // service time
    private int  quantum; // service quantum

    /*
        name : Process ( constructor )
        purpose : initialize the process data
     */

    Process(String name, int arrival, int cycleTime) {
        this.name = name;
        this.arrival = arrival;
        this.cycleTime = cycleTime;
        this.wait = 0;
        this.service = 0;
    }

    /*
        name : getters and setters
        purpose : manipulate process data
     */

    void setWait(int wait) {
        this.wait = wait;
    }

    void setQuantum(int quantum) {
        this.quantum = quantum;
    }


    int getArrival() {
        return arrival;
    }

    int getCycleTime() {
        return cycleTime;
    }

    int getWait() {
        return wait;
    }

    int getService() {
        return service;
    }

    /*
        name : run
        purpose : executes a process
     */

    @Override
    public void run() {
        LocalDateTime d = LocalDateTime.now(); // get the current time
        /*
            purpose : synchronize the waiting line and execute one single process
         */
        synchronized (this){
            System.out.print("\n"+this.name+" : "+d.getHour()+":"+d.getMinute()+":"+d.getSecond()+"-> ");
            for(int i=0;i<quantum;i++){
                try {
                    Thread.sleep( 1000); // make the thread on sleep for one second
                    Scheduler.timer++; // increment the timer by one second
                    service++; // increment the process service time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(". "); // tell the user that his process is on execution
            }
            Scheduler.schedulerString += name+" :"+quantum+" | "; // add the process to the scheduling line
            d = LocalDateTime.now(); // get the current time
            System.out.print("-> "+d.getHour()+":"+d.getMinute()+":"+d.getSecond()+" \\ \\ wait : "+this.getWait()+"\n");
            notify(); // notify other process that the process is finished
        }
    }
}

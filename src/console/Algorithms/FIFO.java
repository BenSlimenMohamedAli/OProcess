package console.Algorithms;

import java.util.ArrayList;

/*
    Description :   In computer programming, FIFO (first-in, first-out) is an approach to handling program
                    work requests from queues or stacks so that the oldest request is handled next.
 */

public class FIFO extends Scheduler {

    @Override
    public void schedule(ArrayList<Process> waiting_line ) throws InterruptedException {
        sortArrival(waiting_line); // sort the waiting line by arrival

        /*
            Purpose : waiting line execution
         */
        while(waiting_line.size() > 0){

            /*
                Purpose : wait until a process comes
            */
            while(timer < waiting_line.get(0).getArrival()){
                Thread.sleep(1000); // sleep the thread for one second
                System.out.print("* ");
                timer++; // increment the timer
            }

            waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime()); // initialise the quantum
            System.out.println(); // escape one line for better view

            totalWaiting += waiting_line.get(0).getWait(); // add the waiting time to the total waiting time
            totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime(); // add the rotation time to the total rotation time

            Thread t = new Thread(waiting_line.get(0)); // initialise the thread
            t.start(); // start the thread
            t.join(); // wait until the thread finishes her cycles

            /*
                Purpose : set the waiting time for the rest of the waiting line
             */
            for(int i=1;i<waiting_line.size();i++){
                if(waiting_line.get(i).getArrival() < timer){
                    waiting_line.get(i).setWait((timer-waiting_line.get(i).getArrival()));
                }
            }
            waiting_line.remove(0); // remove the killed process
        }
        System.out.println("\nAVG waiting time : "+((double)totalWaiting/nbProcesses)); // print the AVG waiting time
        System.out.println("\nAVG service time : "+((double)totalRotation/nbProcesses)); // print the AVG service time
    }
}

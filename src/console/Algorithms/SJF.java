package console.Algorithms;

import java.util.ArrayList;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */
public class SJF extends Scheduler {
    @Override
    public void schedule(ArrayList<Process> list) throws InterruptedException {
        sortArrival(list); // Sort the list using the Arrival

        ArrayList<Process> waiting_line = new ArrayList<>(); // initialize the waiting_line

        /*
            Purpose : execute the waiting line
         */
        while(list.size() > 0 || waiting_line.size() > 0){

            edit_list(list,waiting_line); // add the arrived process to the waiting line

            sortCycleTime(waiting_line); // Sort the waiting line using Cycles time

            waiting(waiting_line); // Wait until the first process comes

            /*
                Purpose : execute the coming process
             */
            if(waiting_line.size() > 0){

                waiting_line.get(0).setWait(timer - (waiting_line.get(0).getArrival())); // increment the process waiting time
                waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime()); // set the process execution quantum

                totalWaiting += waiting_line.get(0).getWait(); // increment the total waiting time
                totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime(); // increment the total rotation time

                Thread t = new Thread(waiting_line.get(0)); // initialize the thread that executes the coming process
                t.start(); // start the process
                t.join(); // wait until the process finishes it's cycles
                waiting_line.remove(0); // remove the killed process from the waiting line
            }
        }

        System.out.println("\nAVG waiting time : "+((double)totalWaiting/nbProcesses)); // print the AVG waiting time
        System.out.println("\nAVG service time : "+((double)totalRotation/nbProcesses)); // print the AVG service time
    }
}

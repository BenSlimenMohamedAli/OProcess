package console.Algorithms;

import java.util.ArrayList;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */

public class SJFP extends Scheduler {

    @Override
    public void schedule(ArrayList<Process> list) throws InterruptedException {
        sortArrival(list); // Sort the list using the arrival

        ArrayList<Process> waiting_line = new ArrayList<>(); // initialize the waiting line


        /*
            Purpose : execute the process
         */
        while (list.size() > 0 || waiting_line.size() > 0) {

            edit_list(list,waiting_line); // add coming process to the waiting line

            sortCycleTime(waiting_line); // sort the waiting line using Cycle time

            waiting(waiting_line); // wait until the next process come

            if (waiting_line.size() > 0) {
                if (waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService() > quantum) {
                    waiting_line.get(0).setQuantum(quantum); // set process quantum

                    Thread t = new Thread(waiting_line.get(0)); // initialize the thread
                    totalWaiting += waiting_line.get(0).getWait(); // increment total waiting
                    totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime(); // increment total rotation time
                    t.start(); // start the process
                    t.join(); // join until the process is done
                } else {
                    waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService());
                    Thread t = new Thread(waiting_line.get(0)); // initialize the thread
                    totalWaiting += waiting_line.get(0).getWait(); // increment total waiting
                    totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime(); // increment total rotation time
                    t.start(); // start the process
                    t.join(); // join until the process is done
                    waiting_line.remove(0); // remove the process from the process from the waiting line
                }
            }
        }

        System.out.println("\nAVG waiting time : "+((double)totalWaiting/nbProcesses)); // print the AVG waiting time
        System.out.println("\nAVG service time : "+((double)totalRotation/nbProcesses)); // print the AVG service time

        System.out.println(schedulerString);

        timer = 0; // reinitialize the timer for next using
        schedulerString = ""; // reinitialize the string for next using
    }

}

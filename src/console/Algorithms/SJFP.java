package console.Algorithms;

import java.util.ArrayList;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */

public class SJFP extends Scheduler {

    @Override
    public void schedule(ArrayList<Process> list) throws InterruptedException {
        sortArrival(list);

        ArrayList<Process> waiting_line = new ArrayList<>();


        while (list.size() > 0 || waiting_line.size() > 0) {

            edit_list(list,waiting_line);

            sortCycleTime(waiting_line);

            waiting(waiting_line);

            if (waiting_line.size() > 0) {
                if (waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService() > quantum) {
                    waiting_line.get(0).setQuantum(quantum);
                    Thread t = new Thread(waiting_line.get(0));
                    totalWaiting += waiting_line.get(0).getWait();
                    totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime();
                    t.start();
                    t.join();
                } else {
                    waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService());
                    Thread t = new Thread(waiting_line.get(0));
                    totalWaiting += waiting_line.get(0).getWait();
                    totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime();
                    t.start();
                    t.join();
                    waiting_line.remove(0);
                }
            }
        }

        System.out.println("\nAVG waiting time : "+((double)totalWaiting/nbProcesses)); // print the AVG waiting time
        System.out.println("\nAVG service time : "+((double)totalRotation/nbProcesses)); // print the AVG service time

        System.out.println(schedulerString);
    }

}

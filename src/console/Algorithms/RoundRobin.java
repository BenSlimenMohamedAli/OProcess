package console.Algorithms;

import java.util.ArrayList;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */

public class RoundRobin extends Scheduler {
    @Override
    public void schedule(ArrayList<Process> list) throws InterruptedException {
        sortArrival(list);

        ArrayList<Process> waiting_line = new ArrayList<>();
        while(list.size() > 0 || waiting_line.size() > 0){

            edit_list(list,waiting_line);

            waiting(waiting_line);

            for(int i=0;i<waiting_line.size();i++){
                waiting_line.get(i).setWait(timer - (waiting_line.get(i).getArrival() + waiting_line.get(i).getService()));


                if(waiting_line.get(i).getCycleTime() - waiting_line.get(i).getService() > quantum){
                    waiting_line.get(i).setQuantum(quantum);
                    Thread t = new Thread(waiting_line.get(i));
                    t.start();
                    t.join();
                } else{
                    waiting_line.get(i).setQuantum(waiting_line.get(i).getCycleTime() - waiting_line.get(i).getService());
                    totalWaiting += waiting_line.get(0).getWait();
                    totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime();
                    Thread t = new Thread(waiting_line.get(i));
                    t.start();
                    t.join();
                    totalWaiting+=waiting_line.get(i).getWait();
                    totalRotation += waiting_line.get(i).getWait()+waiting_line.get(i).getCycleTime();
                    waiting_line.remove(i);
                    i--;
                }

                edit_list(list,waiting_line);
            }
        }

        System.out.println("\nAVG waiting time : "+((double)totalWaiting/nbProcesses)); // print the AVG waiting time
        System.out.println("\nAVG service time : "+((double)totalRotation/nbProcesses)); // print the AVG service time

    }
}

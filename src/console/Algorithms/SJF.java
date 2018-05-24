package console.Algorithms;

import java.util.ArrayList;

public class SJF extends Scheduler {
    @Override
    public void schedule(ArrayList<Process> list) throws InterruptedException {
        sortA(list);

        ArrayList<Process> waiting_line = new ArrayList<>();

        while(list.size() > 0 || waiting_line.size() > 0){

            edit_list(list,waiting_line);

            sortD_cycles(waiting_line);

            waiting(waiting_line);

            if(waiting_line.size() > 0){
                waiting_line.get(0).setWait(timer - (waiting_line.get(0).getArrival()));
                waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime());
                totalWaiting += waiting_line.get(0).getWait();
                totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime();
                Thread t = new Thread(waiting_line.get(0));
                t.start();
                t.join();
                waiting_line.remove(0);
            }
        }
    }
}

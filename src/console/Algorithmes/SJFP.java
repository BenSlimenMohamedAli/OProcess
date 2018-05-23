package console.Algorithmes;

import java.util.ArrayList;

public class SJFP extends Scheduler {

    @Override
    public void ordonnancement(ArrayList<Process> list) throws InterruptedException {
        sortA(list);

        ArrayList<Process> waiting_line = new ArrayList<>();


        while (list.size() > 0 || waiting_line.size() > 0) {

            edit_list(list,waiting_line);

            sortD_cycles(waiting_line);

            waiting(waiting_line);

            if (waiting_line.size() > 0) {
                if (waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService() > quantum) {
                    waiting_line.get(0).setQuantum(quantum);
                    Thread t = new Thread(waiting_line.get(0));
                    t.start();
                    t.join();
                } else {
                    waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime() - waiting_line.get(0).getService());
                    Thread t = new Thread(waiting_line.get(0));
                    t.start();
                    t.join();
                    waiting_line.remove(0);
                }
            }
        }
    }

}

package console.Algorithmes;

import java.util.ArrayList;

public class RoundRobin extends Scheduler {
    @Override
    public void ordonnancement(ArrayList<Process> list) throws InterruptedException {
        sortA(list);

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
        System.out.println("\nLe temps d'Wait moyen est : "+((double)totalWaiting/nbProcessus));
        System.out.println("\nLe temps de rotation moyen est : "+((double)totalRotation/nbProcessus));

    }
}

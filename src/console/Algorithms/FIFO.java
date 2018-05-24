package console.Algorithms;

import java.util.ArrayList;

public class FIFO extends Scheduler {

    @Override
    public void schedule(ArrayList<Process> waiting_line ) throws InterruptedException {
        sortA(waiting_line);

        while(waiting_line.size() > 0){

            while(timer < waiting_line.get(0).getArrival()){
                Thread.sleep(1000);
                System.out.print("* ");
                timer++;
            }

            waiting_line.get(0).setQuantum(waiting_line.get(0).getCycleTime());
            System.out.println();
            totalWaiting += waiting_line.get(0).getWait();
            totalRotation += waiting_line.get(0).getWait()+waiting_line.get(0).getCycleTime();
            Thread t = new Thread(waiting_line.get(0));
            t.start();
            t.join();

            for(int i=1;i<waiting_line.size();i++){
                if(waiting_line.get(i).getArrival() < timer){
                    waiting_line.get(i).setWait((timer-waiting_line.get(i).getArrival()));
                }
            }
            waiting_line.remove(0);
        }
        System.out.println("\nLe temps d'attente moyen est : "+((double)totalWaiting/nbProcessus));
        System.out.println("\nLe temps de rotation moyen est : "+((double)totalRotation/nbProcessus));
    }
}

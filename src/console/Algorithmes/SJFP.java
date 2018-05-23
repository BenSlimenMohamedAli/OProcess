package console.Algorithmes;

import java.util.ArrayList;

public class SJFP extends Ordonnanceur {

    @Override
    public void ordonnancement(ArrayList<Processus> rlist) throws InterruptedException {
        sortA(rlist);

        ArrayList<Processus> filsDattente = new ArrayList<>();


        while (rlist.size() > 0 || filsDattente.size() > 0) {

            edit_list(rlist,filsDattente); // edit the list

            sortD_cycles(filsDattente);

            waiting(filsDattente);

            if (filsDattente.size() > 0) {
                if (filsDattente.get(0).getD_cycles() - filsDattente.get(0).getService() > quantum) {
                    filsDattente.get(0).setQuantum(quantum);
                    Thread t = new Thread(filsDattente.get(0));
                    t.start();
                    t.join();
                } else {
                    filsDattente.get(0).setQuantum(filsDattente.get(0).getD_cycles() - filsDattente.get(0).getService());
                    Thread t = new Thread(filsDattente.get(0));
                    t.start();
                    t.join();
                    filsDattente.remove(0);
                }
            }
        }
    }

}

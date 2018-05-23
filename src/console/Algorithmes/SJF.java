package console.Algorithmes;

import java.util.ArrayList;

public class SJF extends Ordonnanceur {
    @Override
    public void ordonnancement(ArrayList<Processus> sjflist) throws InterruptedException {
        sortA(sjflist); // Trier la liste selon le temp d'arrivée

        ArrayList<Processus> filsDattente = new ArrayList<>();

        while(sjflist.size() > 0 || filsDattente.size() > 0){
            /*
                Ajouter les processus prés pour l'execution a la fils d'attente
             */

            edit_list(sjflist,filsDattente); // edit the list

            sortD_cycles(filsDattente);     // Trier la fils d'attente selon la durée des cycles

            waiting(filsDattente);
            /*
                Execution du processus
             */
            if(filsDattente.size() > 0){
                filsDattente.get(0).setAttente(compteur - (filsDattente.get(0).getArrivee()));  // Modification du temps d'attente
                filsDattente.get(0).setQuantum(filsDattente.get(0).getD_cycles());
                Thread t = new Thread(filsDattente.get(0));
                t.start();
                t.join();
                filsDattente.remove(0);
            }
        }
    }
}

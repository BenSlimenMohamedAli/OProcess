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

            if(sjflist.size() > 0){
                while(sjflist.get(0).getArrivee() <= compteur){
                    filsDattente.add(filsDattente.size(),sjflist.get(0));
                    sjflist.remove(0);

                    if(sjflist.size() == 0)
                        break;
                }
            }
            sortD_cycles(filsDattente);     // Trier la fils d'attente selon la durée des cycles
            /*  Attendre l'arrivée de la prochaine processus */
            if(filsDattente.size() > 0)
                while(compteur < filsDattente.get(0).getArrivee()){
                    Thread.sleep(1000);       //  attendre une seconde
                    System.out.print("* ");
                    compteur++;                     //  L'incrémentation du compteur pour mesurer le temps
                }
            else{
                Thread.sleep(1000);       //  attendre une seconde
                System.out.print("* ");
                compteur++;
            }
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

package console.Algorithmes;

import java.util.ArrayList;

public class FIFO extends Ordonnanceur {

    @Override
    public void ordonnancement(ArrayList<Processus> filsDattente ) throws InterruptedException {
        sortA(filsDattente);   //  La liste d'attente

        while(filsDattente.size() > 0){

            //  Attendre l'arrivée d'une processus
            while(compteur < filsDattente.get(0).getArrivee()){
                Thread.sleep(1000); //  attendre une seconde
                System.out.print("* ");
                compteur++; //  L'incrémentation du compteur pour mesurer le temps
            }

            filsDattente.get(0).setQuantum(filsDattente.get(0).getD_cycles());
            System.out.println();
            attenteTotal += filsDattente.get(0).getAttente();   // Ajouter le temps d'attente du processus
            rotationTotale += filsDattente.get(0).getAttente()+filsDattente.get(0).getD_cycles();   // Ajouter le temps de rotation du processus
            Thread t = new Thread(filsDattente.get(0));// L'ancement du processus courante
            t.start();
            t.join();
            //  incrémentation du temps d'attente des processus
            for(int i=1;i<filsDattente.size();i++){
                if(filsDattente.get(i).getArrivee() < compteur){
                    filsDattente.get(i).setAttente((compteur-filsDattente.get(i).getArrivee()));
                }
            }
            filsDattente.remove(0); //  Tuer une processus
        }
        System.out.println("\nLe temps d'attente moyen est : "+((double)attenteTotal/nb_processus));    //  affichage du temps d'attente moyenne
        System.out.println("\nLe temps de rotation moyen est : "+((double)rotationTotale/nb_processus));    //  affichage du temps de rotation moyenne
    }




}

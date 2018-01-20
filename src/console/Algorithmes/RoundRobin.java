package console.Algorithmes;

import java.util.ArrayList;

public class RoundRobin extends Ordonnanceur {
    @Override
    public void ordonnancement(ArrayList<Processus> rlist) throws InterruptedException {
        sortA(rlist);

        ArrayList<Processus> filsDattente = new ArrayList<>();
        while(rlist.size() > 0 || filsDattente.size() > 0){

            if(rlist.size() > 0){
                while(rlist.get(0).getArrivee() <= compteur){
                    filsDattente.add(filsDattente.size(),rlist.get(0));
                    rlist.remove(0);

                    if(rlist.size() == 0)
                        break;
                }
            }

            if(filsDattente.size() > 0)
                while(compteur < filsDattente.get(0).getArrivee()){
                    Thread.sleep(1000); //  attendre une seconde
                    System.out.print("* ");
                    compteur++; //  L'incrémentation du compteur pour mesurer le temps
                }
            else{
                    Thread.sleep(1000);
                    System.out.print("* ");
                    compteur++;
            }

            for(int i=0;i<filsDattente.size();i++){
                filsDattente.get(i).setAttente(compteur - (filsDattente.get(i).getArrivee() + filsDattente.get(i).getService()));


                if(filsDattente.get(i).getD_cycles() - filsDattente.get(i).getService() > quantum){
                    filsDattente.get(i).setQuantum(quantum);
                    Thread t = new Thread(filsDattente.get(i));
                    t.start();
                    t.join();
                } else{
                    filsDattente.get(i).setQuantum(filsDattente.get(i).getD_cycles() - filsDattente.get(i).getService());
                    Thread t = new Thread(filsDattente.get(i));
                    t.start();
                    t.join();
                    attenteTotal+=filsDattente.get(i).getAttente();
                    rotationTotale += filsDattente.get(i).getAttente()+filsDattente.get(i).getD_cycles();
                    filsDattente.remove(i);
                    i--;
                }

                if(rlist.size() > 0){
                    while(rlist.get(0).getArrivee() <= compteur){
                        filsDattente.add(filsDattente.size(),rlist.get(0));
                        rlist.remove(0);
                        if(rlist.size() == 0)
                            break;
                    }
                }
            }
        }
        System.out.println("\nLe temps d'attente moyen est : "+((double)attenteTotal/nb_processus));    //  affichage du temps d'attente moyenne
        System.out.println("\nLe temps de rotation moyen est : "+((double)rotationTotale/nb_processus));    //  affichage du temps de rotation moyenne

    }
}

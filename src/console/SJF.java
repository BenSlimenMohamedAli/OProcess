package console;

import java.util.ArrayList;

public class SJF extends Ordonnanceur{
    @Override
    public void ordonnancement(ArrayList<Processus> list) throws InterruptedException {
        ArrayList<Processus> sjflist = sortA(list);

        ArrayList<Processus> filsDattente = new ArrayList<>();

        while(sjflist.size() > 0 || filsDattente.size() > 0){

            if(sjflist.size() > 0){
                while(sjflist.get(0).getArrivee() <= compteur){
                    filsDattente.add(filsDattente.size(),sjflist.get(0));
                    sjflist.remove(0);

                    if(sjflist.size() == 0)
                        break;
                }
            }
            sortD_cycles(filsDattente);
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



            if(filsDattente.size() > 0){
                filsDattente.get(0).setQuantum(filsDattente.get(0).getD_cycles());
                Thread t = new Thread(filsDattente.get(0));
                t.start();
                t.join();
                filsDattente.remove(0);
            }
        }
    }
}

package console;

import java.util.ArrayList;

public class RoundRobin extends Ordonnanceur {
    @Override
    public void ordonnancement(ArrayList<Processus> list) throws InterruptedException {
        ArrayList<Processus> rlist = sortA(list);

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
    }
}

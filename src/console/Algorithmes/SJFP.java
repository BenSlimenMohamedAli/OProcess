package console.Algorithmes;

import java.util.ArrayList;

public class SJFP extends Ordonnanceur {

    @Override
    public void ordonnancement(ArrayList<Processus> rlist) throws InterruptedException {
        sortA(rlist);

        ArrayList<Processus> filsDattente = new ArrayList<>();


        while (rlist.size() > 0 || filsDattente.size() > 0) {

            if (rlist.size() > 0) {
                while (rlist.get(0).getArrivee() <= compteur) {
                    filsDattente.add(filsDattente.size(), rlist.get(0));
                    rlist.remove(0);
                    if (rlist.size() == 0)
                        break;
                }
            }
            sortD_cycles(filsDattente);

            if (filsDattente.size() > 0)
                while (compteur < filsDattente.get(0).getArrivee()) {
                    Thread.sleep(1000); //  attendre une seconde
                    System.out.print("* ");
                    compteur++; //  L'incrémentation du compteur pour mesurer le temps
                }
            else {
                Thread.sleep(1000);
                System.out.print("* ");
                compteur++;
            }

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

package console;

import console.Algorithmes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import console.Algorithmes.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean var2 = true;

        int c;
        do {
            System.out.println("1) \u001b[32mSaisir une liste des processus \u001b[00m\n2)\u001b[32m Ordonnancer les processus\u001b[00m \nAutres )\u001b[32m Quitter\u001b[00m ");
            c = in.nextInt();
            switch(c) {
                case 1:
                    Ordonnanceur.saisieProcs("file.txt");
                    break;
                case 2:
                    ArrayList<Processus> list = Ordonnanceur.recupérerProcs("file.txt");
                    boolean var4 = true;

                    int c1;
                    do {
                        System.out.println("    Choisir un algorithme\n1) Tourniquet\n2) SJF\n3) SJF Préemptif\n4) FIFO\nAutres) Retourner");
                        c1 = in.nextInt();
                        switch(c1) {
                            case 1:
                                System.out.println("Donner un quantum");
                                Ordonnanceur.quantum = in.nextInt();
                                RoundRobin rr = new RoundRobin();
                                rr.ordonnancement(list);
                                break;
                            case 2:
                                SJF sjf = new SJF();
                                sjf.ordonnancement(list);
                                break;
                            case 3:
                                System.out.println("Donner un quantum");
                                Ordonnanceur.quantum = in.nextInt();
                                SJFP sjfp = new SJFP();
                                sjfp.ordonnancement(list);
                                break;
                            case 4:
                                FIFO fifo = new FIFO();
                                fifo.ordonnancement(list);
                        }
                    } while(c1 == 1 || c1 == 2 || c1 == 3);
            }
        } while(c == 1 || c == 2);

    }
}

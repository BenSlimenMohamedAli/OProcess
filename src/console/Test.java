package console;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {

        //Ordonnanceur.saisieProcs("file.txt");
        Scanner in = new Scanner(System.in);

        Ordonnanceur.quantum = in.nextInt();

        SJF r = new SJF();
        try {
            r.ordonnancement(Ordonnanceur.recupérerProcs("file.txt"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

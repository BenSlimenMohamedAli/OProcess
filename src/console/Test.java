package console;


import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {

        //Ordonnanceur.saisieProcs("file.txt");


        FIFO f = new FIFO();
        try {
            f.ordonnancement(Ordonnanceur.recupérerProcs("file.txt"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

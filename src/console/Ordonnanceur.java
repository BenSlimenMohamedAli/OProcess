package console;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

abstract class Ordonnanceur {
    public static int compteur;

    public static int attente;

    // La méthode d'ordonnancement
    public abstract void ordonnancement();

    // La méthode du saisie des données dans le fichier

    public static void saisieProcs(String Nfichier) throws IOException {
        int nb_procs;
        Scanner in = new Scanner(System.in);
        int arrivee,d_cycles;
        String nom;
        File f = new File(Nfichier);
        f.createNewFile();
        PrintWriter writer = new PrintWriter(f,"UTF-8");

        System.out.println("Donner le nombre des processus");
        nb_procs = in.nextInt();
        in.nextLine();
        writer.write(nb_procs+"");

        for(int i=0;i<nb_procs;i++){
            System.out.println("\nLa processus "+(i+1));

            System.out.print("Nom : "); // L'ecriture du nom
            nom = in.nextLine();
            System.out.print("Arrivée : ");
            arrivee = in.nextInt();
            System.out.print("durée des cycles : ");
            d_cycles = in.nextInt();
            in.nextLine();

            writer.write("\n"+nom+","+arrivee+","+d_cycles);
        }
        writer.flush();
        writer.close();
    }

}

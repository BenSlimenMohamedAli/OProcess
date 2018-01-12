package console;

import java.awt.*;
import java.io.*;
import java.util.*;

abstract class Ordonnanceur {
    public static int compteur;

    public static int attenteTotal = 0; // Le temps d'attente total
    public static int rotationTotale = 0;   //  Le temps de rotation total
    public static int nb_processus; //  Le nombre des processus
    public static int quantum;

    public abstract void ordonnancement(ArrayList<Processus> flist) throws InterruptedException;    //  La méthode d'ordonnancement

    /*
     *  Nom :               saisieProcs
     *  Fonctionnement :    Le remplissage des processus dans le fichier
     *
     */

    public static void saisieProcs(String Nfichier) throws IOException {
        boolean continuer  = true;  //  Un boolean pour connaitre
        Scanner in = new Scanner(System.in);    //  Un objet Scanner pour récuperer input des utilisateurs
        int arrivee,d_cycles;   //  Des variables pour stocker la date d'arrivée et la durée des cycles
        String nom; //  Une variable pour stocker le nom du procesus
        File f = new File(Nfichier);    //  recupération du fichier
        f.createNewFile();  //  Création du fichier au cas ou elle n'existe pas
        PrintWriter writer = new PrintWriter(f,"UTF-8");    //  définir le writer

        while(continuer){
            try{
                System.out.println("\033[32m"+"Donner le nombre des processus"+"\033[00m");
                nb_processus = in.nextInt();
                writer.write(nb_processus+"");
                continuer = false;
            }catch(InputMismatchException e){
                System.out.println("\n"+"\033[31m"+"Vérifier votre saisie !!"+"\033[00m");
                in.nextLine();
            }
        }
        in.nextLine();
        for(int i=0;i<nb_processus;i++){
            System.out.println("\033[33m"+"\nLa processus "+(i+1)+"\033[00m");

            try{
                System.out.print("\033[35m"+"Nom : "+"\033[00m"); // L'ecriture du nom
                nom = in.nextLine();
                if(nom.contains(","))
                    throw new Exception();
                System.out.print("\033[35m"+"Arrivée : "+"\033[00m");
                arrivee = in.nextInt();
                System.out.print("\033[35m"+"durée des cycles : "+"\033[00m");
                d_cycles = in.nextInt();
                writer.write("\n"+nom+","+arrivee+","+d_cycles);
                in.nextLine();
            }catch(InputMismatchException e){
                System.out.println("\n"+"\033[31m"+"Vérifier votre saisie !!"+"\033[00m");
                i--;
            } catch (Exception e) {
                System.out.println("\n"+"\033[31m"+"Vérifier votre saisie !!"+"\033[00m");
                i--;
            }
        }
        writer.flush();
        writer.close();
    }

    /*
     *  Nom :               recupérerProcs
     *  Fonctionnement :    récupération des données a partir du fichier
     *
     */

    public static ArrayList<Processus> recupérerProcs(String FName) throws IOException {
        ArrayList<Processus> listP = new ArrayList<>();

        File f = new File(FName);
        FileReader reader = new FileReader(f);
        BufferedReader br = new BufferedReader(reader);

        if(f.length() > 0){
            nb_processus = Integer.parseInt(br.readLine());
            String s;
            while((s = br.readLine()) != null){
                if((s.equals("") || s.substring(0,2).equals("//")))
                    continue;
                String[] tab = new String[3];
                tab = s.split(",");
                listP.add(new Processus(tab[0],Integer.parseInt(tab[1]),Integer.parseInt(tab[2])));
            }
        }


        return listP;
    }

    /*
     *  Nom :               sortA
     *  Fonctionnement :    tri selon le temps d'arrivée des processus
     *
     */

    public ArrayList<Processus> sortA(ArrayList<Processus> list){
        Collections.sort(list, new Comparator<Processus>() {
            @Override
            public int compare(Processus o1, Processus o2) {
                if (o1.getArrivee() < o2.getArrivee()) {
                    return -1;
                } else if (o1.getArrivee() > o2.getArrivee()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }

    public void sortD_cycles(ArrayList<Processus> list){
        Collections.sort(list, new Comparator<Processus>() {
            @Override
            public int compare(Processus o1, Processus o2) {
                if ((o1.getD_cycles()-o1.getService()) < (o2.getD_cycles()-o2.getService())) {
                    return -1;
                } else if ((o1.getD_cycles()-o1.getService()) > (o2.getD_cycles()-o2.getService())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

}

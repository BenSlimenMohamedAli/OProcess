package console.Algorithmes;

import java.io.*;
import java.util.*;

public abstract class Ordonnanceur {
    static int compteur;
    static String ordChaine = "";

    public static int quantum;
    static int attenteTotal = 0, nb_processus, rotationTotale = 0; // Le temps d'attente total //  Le temps de rotation total //  Le nombre des processus

    public abstract void ordonnancement(ArrayList<Processus> flist) throws InterruptedException;    //  La méthode d'ordonnancement commune

    /*
     *  Nom :               saisieProcs
     *  Fonctionnement :    Le remplissage des processus dans le fichier
     *
     */

    public static void saisieProcs(String Nfichier) throws IOException {
        boolean continuer  = true;              //  continue or not
        Scanner in = new Scanner(System.in);    //  user choise
        int arrivee,d_cycles;                   //  Comming date
        String nom;                             //  Process name
        File f = new File(Nfichier);            //  getting the file
        f.createNewFile();                      //  Creating a file if it is not exist
        PrintWriter writer = new PrintWriter(f,"UTF-8");    // writer to write into file

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
            }catch(Exception e){
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

    public void sortA(ArrayList<Processus> list){
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
    }

    /*
     *  Nom :               sortD_cycles
     *  Fonctionnement :    tri selon le les durées des cycles
     *
     */

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

    /*
     *  Nom :               edit_list
     *  Fonctionnement :    editer les listes des processus
     *
     */

    public void edit_list(ArrayList<Processus> list , ArrayList<Processus> fils){

        if(list.size() > 0){
            while(list.get(0).getArrivee() <= compteur){
                fils.add(fils.size(),list.get(0));
                list.remove(0);
                if(list.size() == 0)
                    break;
            }
        }
    }

    /*
     *  Nom :               edit_list
     *  Fonctionnement :    editer les listes des processus
     *
     */

    public void waiting(ArrayList<Processus> fils) throws InterruptedException{
        /*  Attendre l'arrivée de la prochaine processus */
        if(fils.size() > 0)
            while(compteur < fils.get(0).getArrivee()){
                Thread.sleep(1000);       //  attendre une seconde
                System.out.print("* ");
                compteur++;                     //  L'incrémentation du compteur pour mesurer le temps
            }
        else{
            Thread.sleep(1000);       //  attendre une seconde
            System.out.print("* ");
            compteur++;
        }
    }

}

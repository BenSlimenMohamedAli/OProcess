package console.Algorithmes;

import java.io.*;
import java.util.*;

public abstract class Scheduler {
    static int timer;
    static String ordChaine = "";

    public static int quantum;
    static int totalWaiting = 0, nbProcessus, totalRotation = 0;

    public abstract void ordonnancement(ArrayList<Process> flist) throws InterruptedException;


    public static void saisieProcs(String Nfichier) throws IOException {
        boolean continuer  = true;
        Scanner in = new Scanner(System.in);
        int arrival,d_cycles;
        String name;
        File f = new File(Nfichier);
        f.createNewFile();
        PrintWriter writer = new PrintWriter(f,"UTF-8");

        while(continuer){
            try{
                System.out.println("\033[32m"+"Donner le namebre des process"+"\033[00m");
                nbProcessus = in.nextInt();
                writer.write(nbProcessus+"");
                continuer = false;
            }catch(InputMismatchException e){
                System.out.println("\n"+"\033[31m"+"Vérifier votre saisie !!"+"\033[00m");
                in.nextLine();
            }
        }

        in.nextLine();
        for(int i=0;i<nbProcessus;i++){
            System.out.println("\033[33m"+"\nLa process "+(i+1)+"\033[00m");

            try{
                System.out.print("\033[35m"+"name : "+"\033[00m");
                name = in.nextLine();
                if(name.contains(","))
                    throw new Exception();
                System.out.print("\033[35m"+"Arrivée : "+"\033[00m");
                arrival = in.nextInt();
                System.out.print("\033[35m"+"durée des cycles : "+"\033[00m");
                d_cycles = in.nextInt();
                writer.write("\n"+name+","+arrival+","+d_cycles);
                in.nextLine();
            }catch(Exception e){
                System.out.println("\n"+"\033[31m"+"Vérifier votre saisie !!"+"\033[00m");
                i--;
            }
        }
        writer.flush();
        writer.close();
    }

    public static ArrayList<Process> recupérerProcs(String FName) throws IOException {
        ArrayList<Process> listP = new ArrayList<>();

        File f = new File(FName);
        FileReader reader = new FileReader(f);
        BufferedReader br = new BufferedReader(reader);

        if(f.length() > 0){
            nbProcessus = Integer.parseInt(br.readLine());
            String s;
            while((s = br.readLine()) != null){
                if((s.equals("") || s.substring(0,2).equals("//")))
                    continue;
                String[] tab = new String[3];
                tab = s.split(",");
                listP.add(new Process(tab[0],Integer.parseInt(tab[1]),Integer.parseInt(tab[2])));
            }
        }


        return listP;
    }


    public void sortA(ArrayList<Process> list){
        Collections.sort(list, new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if (o1.getArrival() < o2.getArrival()) {
                    return -1;
                } else if (o1.getArrival() > o2.getArrival()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    public void sortD_cycles(ArrayList<Process> list){
        Collections.sort(list, new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                if ((o1.getCycleTime()-o1.getService()) < (o2.getCycleTime()-o2.getService())) {
                    return -1;
                } else if ((o1.getCycleTime()-o1.getService()) > (o2.getCycleTime()-o2.getService())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }


    public void edit_list(ArrayList<Process> list , ArrayList<Process> fils){

        if(list.size() > 0){
            while(list.get(0).getArrival() <= timer){
                fils.add(fils.size(),list.get(0));
                list.remove(0);
                if(list.size() == 0)
                    break;
            }
        }
    }


    public void waiting(ArrayList<Process> fils) throws InterruptedException{
        if(fils.size() > 0)
            while(timer < fils.get(0).getArrival()){
                Thread.sleep(1000);
                System.out.print("* ");
                timer++;
            }
        else{
            Thread.sleep(1000);
            System.out.print("* ");
            timer++;
        }
    }

}

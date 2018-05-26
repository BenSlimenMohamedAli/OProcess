package console.Algorithms;

import java.io.*;
import java.util.*;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */

public abstract class Scheduler {
    static int timer; // execution timer
    static String schedulerString = ""; // Processes final sched

    public static int quantum; // The general quantum for SJF and RR
    static int totalWaiting = 0; // total waiting time
    static int totalRotation = 0; // total rotation time
    static int nbProcesses ; // total number of processes

    /*
        name : schedule
        type : abstract
        purpose : schedule processes
     */
    public abstract void schedule(ArrayList<Process> processes_list) throws InterruptedException;

    /*
        name : inputProcesses
        type : public
        purpose : print processes list to the file
     */

    public static void inputProcesses(String file_name) throws IOException {
        boolean notYet  = true;
        Scanner in = new Scanner(System.in);
        int arrival,cycleTime ;
        String name;
        File f = new File(file_name);
        f.createNewFile();
        PrintWriter writer = new PrintWriter(f,"UTF-8");

        while(notYet ){
            try{
                System.out.println("Give process number");
                nbProcesses = in.nextInt();
                writer.write(nbProcesses+"");
                notYet  = false;
            }catch(InputMismatchException e){
                System.out.println("\nVerify your input !!");
                in.nextLine();
            }
        }

        in.nextLine();
        for(int i=0;i<nbProcesses;i++){
            System.out.println("\nprocess "+(i+1));

            try{
                System.out.print("name : ");
                name = in.nextLine();
                if(name.contains(","))
                    throw new Exception();
                System.out.print("Arrival : ");
                arrival = in.nextInt();
                System.out.print("Cycles time : ");
                cycleTime = in.nextInt();
                writer.write("\n"+name+","+arrival+","+cycleTime );
                in.nextLine();
            }catch(Exception e){
                System.out.println("\nVerify your input !!");
                i--;
            }
        }
        writer.flush();
        writer.close();
    }

    /*
        name : getFromFile
        type : public
        purpose : get processes list from the file
     */

    public static ArrayList<Process> getFromFile(String FName) throws IOException {
        ArrayList<Process> listP = new ArrayList<>();

        File f = new File(FName);
        FileReader reader = new FileReader(f);
        BufferedReader br = new BufferedReader(reader);

        if(f.length() > 0){
            nbProcesses = Integer.parseInt(br.readLine());
            String s;
            while((s = br.readLine()) != null){
                if((s.equals("") || s.substring(0,2).equals("//")))
                    continue;
                String[] tab ;
                tab = s.split(",");
                listP.add(new Process(tab[0],Integer.parseInt(tab[1]),Integer.parseInt(tab[2])));
            }
        }


        return listP;
    }

    /*
        name : sortArrival
        type : package private
        purpose : sort processes list using arrival
     */
    void sortArrival(ArrayList<Process> list){
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

    /*
        name : sortCycleTime
        type : package private
        purpose : sort processes list using cycles time
     */
    void sortCycleTime(ArrayList<Process> list){
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

    /*
        name : edit_list
        type : package private
        purpose : add the coming process to the waiting time
     */
    void edit_list(ArrayList<Process> list , ArrayList<Process> waiting_line){

        if(list.size() > 0){
            while(list.get(0).getArrival() <= timer){
                waiting_line.add(waiting_line.size(),list.get(0));
                list.remove(0);
                if(list.size() == 0)
                    break;
            }
        }
    }

    /*
        name : waiting
        type : package private
        purpose : wait until the next process comes
     */

    void waiting(ArrayList<Process> waiting_line) throws InterruptedException{
        if(waiting_line.size() > 0)
            while(timer < waiting_line.get(0).getArrival()){
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

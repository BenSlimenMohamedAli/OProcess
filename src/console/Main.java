package console;

import console.Algorithms.*;
import console.Algorithms.Process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Description :   Shortest job first is a scheduling algorithm in
                    which the process with the smallest execution time is selected for execution next.
 */

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in); // Read the user input

        int c;
        do {
            System.out.println("1) \u001b[32mSaisir une liste des processus \u001b[00m\n2)\u001b[32m Ordonnancer les processus\u001b[00m \nAutres )\u001b[32m Quitter\u001b[00m ");
            c = in.nextInt();
            switch(c) {
                case 1:
                    Scheduler.inputProcesses("file.txt");
                    break;
                case 2:
                    ArrayList<Process> list = Scheduler.getFromFile("file.txt");
                    
                    System.out.println("    Choisir un algorithme\n1) Tourniquet\n2) SJF\n3) SJF Préemptif\n4) FIFO\nAutres) Retourner");
                        int c1 = in.nextInt();
                        switch(c1) {
                            case 1:
                                System.out.println("Donner un quantum");
                                Scheduler.quantum = in.nextInt();
                                RoundRobin rr = new RoundRobin();
                                rr.schedule(list);
                                break;
                            case 2:
                                SJF sjf = new SJF();
                                sjf.schedule(list);
                                break;
                            case 3:
                                System.out.println("Donner un quantum");
                                Scheduler.quantum = in.nextInt();
                                SJFP sjfp = new SJFP();
                                sjfp.schedule(list);
                                break;
                            default :
                                FIFO fifo = new FIFO();
                                fifo.schedule(list);
                        }
            }
        } while(c == 1 || c == 2);

    }
}

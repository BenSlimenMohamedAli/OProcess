package console;

import console.Algorithms.*;
import console.Algorithms.Process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
    Description :   Main logic

    Collaborators : Ben slimen mohammed ali
                    Bouazizi salma
                    Weslati mohammed amine
 */

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in); // Read the user input

        int c;
        do{
            System.out.println("* * * * * * * * * * * * * * * * * *");
            System.out.println("1) Add processes \n2) Schedule processus \n0) Quit ");
            System.out.println("* * * * * * * * * * * * * * * * * *");
            c = in.nextInt();
            switch(c) {
                case 1:
                    Scheduler.inputProcesses("file.txt");
                    break;
                case 2:
                    int c1;
                    do{
                        System.out.println("* * * * * * * * * * * * * * * * * *");
                        System.out.println("Choose algorithm\n1) Round Robin\n2) SJF\n3) Preamptif SJF\n4) FIFO \n0) Quit");
                        System.out.println("* * * * * * * * * * * * * * * * * *");
                        c1 = in.nextInt();
                        ArrayList<Process> list;
                        switch(c1) {
                            case 0 : break;
                            case 1:
                                list = Scheduler.getFromFile("file.txt");
                                System.out.println("Donner un quantum");
                                Scheduler.quantum = in.nextInt();
                                RoundRobin rr = new RoundRobin();
                                rr.schedule(list);
                                break;
                            case 2:
                                list = Scheduler.getFromFile("file.txt");
                                SJF sjf = new SJF();
                                sjf.schedule(list);
                                break;
                            case 3:
                                list = Scheduler.getFromFile("file.txt");
                                System.out.println("Donner un quantum");
                                Scheduler.quantum = in.nextInt();
                                SJFP sjfp = new SJFP();
                                sjfp.schedule(list);
                                break;
                            default :
                                list = Scheduler.getFromFile("file.txt");
                                FIFO fifo = new FIFO();
                                fifo.schedule(list);
                        }
                    } while(c1 != 0);
                default :
                    System.out.println("Verify your input"); break;
            }
        }while(c != 0);

    }
}

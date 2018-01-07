package console;

import sun.util.calendar.BaseCalendar;

import java.util.Date;

import static java.lang.Thread.sleep;

public class Processus extends Thread{

    private String nom;
    private int arrivee;
    private int d_cycles;
    private int attente;
    private int service;

    //  Le constructeur qui initialise les valeurs
    public Processus(String nom, int arrivee, int d_cycles) {
        this.nom = nom;
        this.arrivee = arrivee;
        this.d_cycles = d_cycles;
        this.attente = 0;
        this.service = 0;
    }

    // les setters


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setArrivee(int arrivee) {
        this.arrivee = arrivee;
    }

    public void setD_cycles(int d_cycles) {
        this.d_cycles = d_cycles;
    }

    public void setAttente(int attente) {
        this.attente = attente;
    }

    public void setService(int service) {
        this.service = service;
    }


    //  Les getters


    public String getNom() {
        return nom;
    }

    public int getArrivee() {
        return arrivee;
    }

    public int getD_cycles() {
        return d_cycles;
    }

    public int getAttente() {
        return attente;
    }

    public int getService() {
        return service;
    }

    //  La méthode run
    @Override
    public void run() {
        Date d = new Date();
        synchronized (this){
            System.out.print("\033[35m"+this.nom+" : "+"\033[00m"+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"-> ");
            for(int i=0;i<d_cycles;i++){
                try {
                    Thread.sleep( 1000);
                    Ordonnanceur.compteur++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(". ");
            }
            d = new Date();
            System.out.print("-> "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"\033[35m"+" \\ \\ Attente : "+"\033[00m"+this.getAttente()+"\n");
            notify();
        }
    }

    // La methode pour lancer le thread selon l'algorithme FIFO
    public void lancerFIFO(Processus p) throws InterruptedException {
        synchronized (p){
            p.wait();
        }
        this.start();
    }

    public void lancerFIFO() throws InterruptedException {
        this.start();
        this.join();
    }

    //  La méthode toString()

    @Override
    public String toString() {
        return nom+" "+arrivee+" "+d_cycles;
    }
}

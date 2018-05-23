package console.Algorithmes;


import java.util.Date;

public class Processus implements Runnable{

    private String nom;
    private int arrivee, d_cycles, attente, service, quantum;

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

    public void setQuantum(int quantum) {
        this.quantum = quantum;
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

    public int getQuantum() {
        return quantum;
    }

    //  La méthode run
    @Override
    public void run() {
        Date d = new Date();
        synchronized (this){
            System.out.print("\n\033[35m"+this.nom+" : "+"\033[00m"+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"-> ");
            for(int i=0;i<quantum;i++){
                try {
                    Thread.sleep( 1000);
                    Ordonnanceur.compteur++;
                    service++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(". ");
            }
            Ordonnanceur.ordChaine += nom+" :"+quantum+" | ";
            d = new Date();
            System.out.print("-> "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"\033[35m"+" \\ \\ Attente : "+"\033[00m"+this.getAttente()+"\n");
            notify();
        }
    }
}

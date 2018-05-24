package console.Algorithms;


import java.util.Date;

public class Process implements Runnable{

    private String name;
    private int arrival, cycleTime, wait, service, quantum;

    Process(String name, int arrival, int cycleTime) {
        this.name = name;
        this.arrival = arrival;
        this.cycleTime = cycleTime;
        this.wait = 0;
        this.service = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    void setWait(int wait) {
        this.wait = wait;
    }

    public void setService(int service) {
        this.service = service;
    }

    void setQuantum(int quantum) {
        this.quantum = quantum;
    }


    public String getName() {
        return name;
    }

    int getArrival() {
        return arrival;
    }

    int getCycleTime() {
        return cycleTime;
    }

    int getWait() {
        return wait;
    }

    int getService() {
        return service;
    }

    public int getQuantum() {
        return quantum;
    }

    @Override
    public void run() {
        Date d = new Date();
        synchronized (this){
            System.out.print("\n\033[35m"+this.name+" : "+"\033[00m"+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"-> ");
            for(int i=0;i<quantum;i++){
                try {
                    Thread.sleep( 1000);
                    Scheduler.timer++;
                    service++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(". ");
            }
            Scheduler.schedString += name+" :"+quantum+" | ";
            d = new Date();
            System.out.print("-> "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"\033[35m"+" \\ \\ wait : "+"\033[00m"+this.getWait()+"\n");
            notify();
        }
    }
}

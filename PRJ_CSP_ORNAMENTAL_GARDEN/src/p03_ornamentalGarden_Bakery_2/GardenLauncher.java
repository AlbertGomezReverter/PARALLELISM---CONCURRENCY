package p03_ornamentalGarden_Bakery_2;

/* EXECUTE this program several times and it will eventually produce an erroneous result */

public class GardenLauncher {
    
    public static InterruptibleCounter iCounter;
    
    public static void main (String [] args) throws InterruptedException{
        
        Turnstile_Bakery_2 west, east;
        
        iCounter = new InterruptibleCounter();
        
        west = new Turnstile_Bakery_2("West", iCounter);
        east = new Turnstile_Bakery_2("East", iCounter);
        
        west.start();
        east.start();
        
        while (west.isAlive() || east.isAlive()) {
                Thread.sleep(50);
                System.out.println("Global counter: "+iCounter.getValue());
        }
        System.out.println();
        System.out.println("FINAL GLOBAL COUNTER: "+iCounter.getValue());  
    }
}

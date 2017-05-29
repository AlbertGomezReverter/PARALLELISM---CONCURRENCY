package p03_ornamentalGarden_Peterson;


public class GardenLauncher {
    
    public static InterruptibleCounter iCounter;
    
    public static void main (String [] args) throws InterruptedException{
        
        Turnstile_Peterson west, east;

        iCounter = new InterruptibleCounter();
        
        west = new Turnstile_Peterson("West", iCounter);
        east = new Turnstile_Peterson("East", iCounter);
        
        west.start();
        east.start();
        
        while (west.isAlive() || east.isAlive()) {
                Thread.sleep(500);
                System.out.println("Global counter: "+iCounter.getValue());
        }
        System.out.println();
        System.out.println("FINAL GLOBAL COUNTER: "+iCounter.getValue());  
    }
}

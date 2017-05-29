package p03_ornamentalGarden_Dekker;



public class GardenLauncher {
    
    public static InterruptibleCounter iCounter;
    
    public static void main (String [] args) throws InterruptedException{
        
        Turnstile_Dekker west, east;

        iCounter = new InterruptibleCounter();
        
        west = new Turnstile_Dekker("West", iCounter);
        east = new Turnstile_Dekker("East", iCounter);
        
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

package p03_ornamentalGarden;


public class GardenLauncher {
    
    public static void main (String [] args) throws InterruptedException{
        
        Turnstile west, east;
        InterruptibleCounter iCounter;
        
        iCounter = new InterruptibleCounter();
        
        west = new Turnstile("West", iCounter);
        east = new Turnstile("East", iCounter);
        
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

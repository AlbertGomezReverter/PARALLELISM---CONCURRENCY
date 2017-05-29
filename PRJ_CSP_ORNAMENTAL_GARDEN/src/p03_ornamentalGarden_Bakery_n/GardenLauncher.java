package p03_ornamentalGarden_Bakery_n;

public class GardenLauncher {
    
    public static InterruptibleCounter iCounter;
    
    public static void main (String [] args) throws InterruptedException{
        
        Turnstile_Bakery_N west, east, north, south;


        iCounter = new InterruptibleCounter();
        
        west = new Turnstile_Bakery_N("West", iCounter);
        east = new Turnstile_Bakery_N("East", iCounter);
        north = new Turnstile_Bakery_N("North", iCounter);
        south = new Turnstile_Bakery_N("South", iCounter);
        
        west.start();
        east.start();
        north.start();
        south.start();
        
        while (west.isAlive() || east.isAlive() || north.isAlive() || south.isAlive()) {
                Thread.sleep(5);
                System.out.println("Global counter: "+iCounter.getValue());
        }
        System.out.println();
        System.out.println("FINAL GLOBAL COUNTER: "+iCounter.getValue());  
    }
}

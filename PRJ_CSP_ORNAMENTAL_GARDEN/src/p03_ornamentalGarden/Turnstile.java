package p03_ornamentalGarden;


public class Turnstile extends Thread {
    
    private InterruptibleCounter globalCounter;
    private int myCounter;
    
    public Turnstile (String name, InterruptibleCounter gCounter) {
        super(name);
        this.globalCounter = gCounter;
        this.myCounter = 0;
    }
    
    @Override
    public void run () {
            // allow 20 people to enter, one person each 1/2 second
            for (int i=1; i<=20; i++) {
                try{Thread.sleep(500);} catch(InterruptedException ie){}
                this.myCounter++;
                this.globalCounter.increment();
                System.out.println(this.getName()+": "+myCounter);
            }
    }
}

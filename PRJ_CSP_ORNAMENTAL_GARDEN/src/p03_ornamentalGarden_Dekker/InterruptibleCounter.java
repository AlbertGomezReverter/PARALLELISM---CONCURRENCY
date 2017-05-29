package p03_ornamentalGarden_Dekker;

public class InterruptibleCounter {
    
    private int value = 0;
    
    public int getValue () {
        return this.value;
    }
    
    public void increment () {
        int temp;
        
        temp = value; // read value
        simulateHWInterrupt();
        value = temp+1; // write value
    }
    
    private static void simulateHWInterrupt () {
        if (Math.random()>0.5) {
            Thread.yield();
        }
    } 
}

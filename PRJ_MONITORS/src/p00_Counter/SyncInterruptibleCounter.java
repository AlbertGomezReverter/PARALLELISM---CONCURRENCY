package p00_Counter;

public class SyncInterruptibleCounter {
    
    private int value = 0;
    
    public synchronized int getValue () {
        return this.value;
    }
    
    public synchronized void increment () {
        int temp;
        
        temp = value; // read value
        simulateHWInterrupt();
        value = temp+1; // write value
    }
    
    private static void simulateHWInterrupt() {
        if (Math.random()>0.5) {
            Thread.yield();
        }
    }
}

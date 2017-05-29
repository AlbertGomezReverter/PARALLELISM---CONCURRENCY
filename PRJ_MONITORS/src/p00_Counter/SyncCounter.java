package p00_Counter;

public class SyncCounter {
    
    private int value; // the shared and protected resource
    
    public synchronized void increment () {
        value++;
    }
    
    public synchronized void decrement () {
        value--;
    }
    
    public synchronized int getValue () {
        return value;
    }
    
}
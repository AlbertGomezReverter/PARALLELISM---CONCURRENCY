package p03_ornamentalGarden_Bakery_2;

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
        if (Math.random()>0.1) {
            Thread.yield();
        }
    } 
}

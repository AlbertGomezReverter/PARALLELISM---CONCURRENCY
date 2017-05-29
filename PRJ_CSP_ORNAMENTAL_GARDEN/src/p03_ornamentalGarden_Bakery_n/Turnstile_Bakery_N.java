package p03_ornamentalGarden_Bakery_n;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicBoolean;

public class Turnstile_Bakery_N extends Thread {

    private static final int MAX_THREADS = 4;
    private static final AtomicInteger number[] = new AtomicInteger[MAX_THREADS];
    private static final AtomicBoolean choosing[] = new AtomicBoolean[MAX_THREADS];
    private static int nextPrNumber = 0;

    // static initializer
    static {
        for (int i = 0; i < MAX_THREADS; i++) {
            number[i] = new AtomicInteger(0);
            choosing[i]= new AtomicBoolean(false);
        }
    }
    
    private InterruptibleCounter globalCounter;
    private int myCounter;
    private int procNumber; 

    public Turnstile_Bakery_N(String name, InterruptibleCounter gCounter) {
        super(name);

        this.procNumber = Turnstile_Bakery_N.nextPrNumber;
        Turnstile_Bakery_N.nextPrNumber++;
        this.globalCounter = gCounter;
        this.myCounter = 0;
    }

    @Override
    public void run() {
        // allow 20 people to enter, one person each 1/2 second
        for (int i = 1; i <= 20; i++) {
            /* Non-Critical Section */
            try{Thread.sleep(5);}catch(InterruptedException ie){};
            this.myCounter++;
            /* preprotocol */
            choosing[this.procNumber].set(true);
            number[this.procNumber].set(1 + max(number));
            choosing[this.procNumber].set(false) ;
            for (int j = 0; j < nextPrNumber; j++) {
                if (this.procNumber != j) {
                    
                    while (choosing[j].get()) {/* busy wait*/}
                    
                    while (number[j].get() > 0
                           && (number[j].get() < number[this.procNumber].get())
                           || (number[j].get() == number[this.procNumber].get()
                               && j < this.procNumber)) {/* busy wait*/}
                }
            }
            /* Critical Section */
            this.globalCounter.increment();
            /* postprotocol */
            number[this.procNumber].set(0);
            /* Non-Critical Section */
            System.out.println(this.getName() + ": " + myCounter);
        }
    }

    private static int max(AtomicInteger t[]) {
        int pm = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i].get() > t[pm].get()) {
                pm = i;
            }
        }
        return t[pm].get();
    }

}

package p03_ornamentalGarden_Peterson;

import java.util.concurrent.atomic.AtomicBoolean;

public class Turnstile_Peterson extends Thread {

    private static AtomicBoolean want[] = {new AtomicBoolean(false),
                                           new AtomicBoolean(false)};
    // want[0] is want_p; want[1] is want_q;
    private static volatile int turn = 0;
    private static int nextPrNumber = 0;
    private InterruptibleCounter globalCounter;
    private int myCounter;
    private int procNumber; // process number. 0 is p; 1 is q
    private int other; // process number of the other process

    public Turnstile_Peterson(String name, InterruptibleCounter gCounter) {
        super(name);

        this.procNumber = Turnstile_Peterson.nextPrNumber;
        Turnstile_Peterson.nextPrNumber++;
        this.other = (this.procNumber + 1) % 2;
        this.globalCounter = gCounter;
        this.myCounter = 0;
    }

    @Override
    public void run() {
        // allow 20 people to enter, one person each 1/2 second
        for (int i = 1; i <= 20; i++) {
            /* Non-Critical Section */
            try {Thread.sleep(500);} catch (InterruptedException ie) {}
            this.myCounter++;
            /* preprotocol */
            want[this.procNumber].set(true);
            turn = this.other;
            while (want[this.other].get() & turn == this.other) {
                // busy wait
            }
            /* Critical Section */
            this.globalCounter.increment();
            /* postprotocol */
            want[this.procNumber].set(false);
            /* Non-Critical Section */
            System.out.println(this.getName() + ": " + myCounter);
        }
    }
}

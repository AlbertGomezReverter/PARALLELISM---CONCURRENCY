package p03_ornamentalGarden_Bakery_2;

/* BEWARE: as operations "np = nq+1" and "nq = np+1" are not atomic 
 * this implementation DOES NOT guarantee mutual exclusion! 
 */

public class Turnstile_Bakery_2 extends Thread {

    private static volatile int np = 0;
    private static volatile int nq = 0;
    private static int nextPrNumber = 1;
    private InterruptibleCounter globalCounter;
    private int myCounter;
    private int procNumber; // process number. 1 is p; 2 is q

    public Turnstile_Bakery_2(String name, InterruptibleCounter gCounter) {
        super(name);

        this.procNumber = Turnstile_Bakery_2.nextPrNumber;
        Turnstile_Bakery_2.nextPrNumber++;
        this.globalCounter = gCounter;
        this.myCounter = 0;
    }

    @Override
    public void run() {
        // allow 20 people to enter, one person each 1/2 second
        for (int i = 1; i <= 20; i++) {
            /* Non-Critical Section */
            try {Thread.sleep(2);} catch(InterruptedException ie){}
            this.myCounter++;
            /* preprotocol */
            if (this.procNumber == 1) { // I'm process p
                np = nq + 1;
                while (nq != 0 & np > nq) {/* busy wait */

                }
            } else { // I'm process q
                nq = np + 1;
                while (np != 0 & nq >= np) {/* busy wait */

                }
            }
            /* Critical Section */
            this.globalCounter.increment();
            /* postprotocol */
            if (this.procNumber == 1) { // I'm process p
                np = 0;
            } else { // I'm process q
                nq = 0;
            }
            /* Non-Critical Section */
            System.out.println(this.getName() + ": " + myCounter);
        }
    }

}

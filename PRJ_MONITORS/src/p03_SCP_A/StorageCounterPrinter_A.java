package p03_SCP_A;

public class StorageCounterPrinter_A {

    public static void main (String [] args) {

        Storage storage = new Storage();
        Printer printer = new Printer(storage);
        Counter counter = new Counter(storage);

        printer.start();
        counter.start();

        try{Thread.sleep(3000);} catch(InterruptedException ie) {}

        System.exit(0);
    }
}

class Printer extends Thread {
    private Storage storage;

    public Printer (Storage storage) {
        this.storage = storage;
    }
    @Override
    public void run () {
        int value;
        while (true) {
            value = storage.retrieve();
            for (int i=0; i<=value; i++) System.out.print(" ");
            System.out.println(value);
        }
    }
}

class Counter extends Thread {
    private Storage storage;

    public Counter (Storage storage) {
        this.storage = storage;
    }
    @Override
    public void run () {
        while (true) {
            for (int i=0; i<=9; i++) {
                try{sleep((long)Math.random()*1000);} catch(InterruptedException ie) {}
                storage.store(i);
            }
        }
    }
}

class Storage {
// this class IS the monitor

    private int value;
    private boolean canStore;

    public synchronized void store (int value) {
        while (!canStore)
            try{wait();} catch(InterruptedException ie) {}

        this.value = value;
        canStore = false;
        notify();
    }

    public synchronized int retrieve () {
        while (canStore)
            try{wait();} catch(InterruptedException ie) {}

        canStore = true;
        notify();
        return (value);
    }
}

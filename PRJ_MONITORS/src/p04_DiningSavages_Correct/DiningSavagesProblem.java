package p04_DiningSavages_Correct;

public class DiningSavagesProblem {
    public static void main (String [] args) {
        
        DiningSavagesMonitor monitor = new DiningSavagesMonitor(4);
        Cook cook = new Cook(monitor);
        Savage tribe [] = new Savage[10];
        for (int i=0; i<tribe.length; i++) {
            tribe[i] = new Savage(monitor);
            tribe[i].start();
        }
        cook.start();
        
        try{Thread.sleep(10000);}catch(InterruptedException ie){}
        
        System.exit(0);
    }
}

class Savage extends Thread  {
    
    private static int nextId = 1;
    
    private int id;
    private DiningSavagesMonitor monitor;
    
    public Savage (DiningSavagesMonitor monitor) {
        this.monitor = monitor;
        this.id = nextId;
        nextId++;
    }
    
    @Override
    public void run () {
        while (true) {
        	monitor.wantToEat();
        	try{sleep(100);}catch(InterruptedException ie){}
            monitor.helpYourself(id);
            try{sleep(200);}catch(InterruptedException ie){}
        }
    }
}

class Cook extends Thread  {
    
    private DiningSavagesMonitor monitor;
    
    public Cook (DiningSavagesMonitor monitor) {
        this.monitor = monitor;
    }
    
    @Override
    public void run () {
        while (true) {
            monitor.refill();
        }
    }
}

class DiningSavagesMonitor {
    private final int CAPACITY;
    private int servings;
    private boolean pleaseRefill = false;
    private boolean potIsFree = true;
    
    public DiningSavagesMonitor(int capacity) {
        CAPACITY = capacity;
        servings = CAPACITY;
    }
    
    public synchronized void wantToEat () {
    	while (!potIsFree)
    		try{wait();}catch(InterruptedException ie) {}
    	
    	potIsFree=false;
    }
    
    public synchronized void helpYourself(int id) {
        while (servings==0) {
        	System.out.println("    -- Savage "+id+" finds the pot empty and tries to wake up the cook");
        	pleaseRefill = true;
            notifyAll(); // try to awaken the cook
            try {wait();} catch(InterruptedException ie){}
        }
        
        System.out.println("yum, yum ("+id+")");
        servings--;
        potIsFree=true;
        notifyAll(); // try to awaken someone else who wants to eat...
    }
    
    public synchronized void refill () {
        while (!pleaseRefill)
            try {wait();} catch(InterruptedException ie){}
        
        pleaseRefill = false;
        servings = CAPACITY;
        System.out.println("REFILLED!");
        notifyAll(); 
    }
}

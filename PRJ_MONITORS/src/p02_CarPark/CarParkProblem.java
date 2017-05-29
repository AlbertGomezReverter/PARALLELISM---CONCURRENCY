package p02_CarPark;

public class CarParkProblem {

    public static void main(String[] args) {
        CarParkMonitor monitor = new CarParkMonitor(4);

        Car[] fleet = new Car[10];
        for (int i = 0; i < fleet.length; i++) {
            fleet[i] = new Car(i+1, monitor);
            fleet[i].start();
        }

        try{Thread.sleep(2000);} catch(InterruptedException ie) {}

        System.exit(0);
    }
}

class CarParkMonitor {

    private final int MAX_CAPACITY;
    private int numCars;

    public CarParkMonitor(int maxCapacity) {
        this.MAX_CAPACITY = maxCapacity;
    }

    public synchronized void enter (int id) {
        while (numCars == MAX_CAPACITY)
            try{wait();}catch(InterruptedException ie){}

        numCars++;

        System.out.println("CAR "+id+" is INSIDE NOW. "
                + "Remaining places: "+(MAX_CAPACITY-numCars));
    }

    public synchronized void exit (int id) {
        numCars--;

        System.out.println("    CAR "+id+" has EXITED the car park. "
                + "Remaining places: "+(MAX_CAPACITY-numCars));

        notify();
    }
}


class Car extends Thread {

    private static final long DRIVE = 2000;
    private static final long PARK = 1000;

    private int id;
    private CarParkMonitor monitor;

    public Car (int id, CarParkMonitor monitor) {
        this.id = id;
        this.monitor = monitor;
    }

    @Override
    public void run () {
        long drivingTime = (long)(Math.random()*DRIVE);
        long parkingTime = (long)(Math.random()*PARK);

        while (true) {
            // drive around for a while
            try{Thread.sleep(drivingTime);} catch(InterruptedException ie){}
            // park
            //System.out.println("CAR "+id+" tries to get into the car park");
            monitor.enter(id);
            // now stay in the parking lot for a while
            
            try{Thread.sleep(parkingTime);} catch(InterruptedException ie){}
            //exit
            monitor.exit(id);
        }
    }
}
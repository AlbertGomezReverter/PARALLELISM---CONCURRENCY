package p05_rollerCoaster;

import java.util.concurrent.atomic.AtomicBoolean;

public class Launcher {
	
	public static void main (String [] args) {
		
		AtomicBoolean running = new AtomicBoolean(false);
		RollerCoasterMonitor monitor = new RollerCoasterMonitor();
		
		Passenger passengers [] = new Passenger[10];
		Car car = new Car(running, monitor);
		
		for (int i=0; i<passengers.length; i++) {
			passengers[i] = new Passenger(running, monitor);
			passengers[i].start();
		}
		car.start();
		
		try{Thread.sleep(50000);} catch(InterruptedException ie){}
		System.exit(0);
		
	}

}

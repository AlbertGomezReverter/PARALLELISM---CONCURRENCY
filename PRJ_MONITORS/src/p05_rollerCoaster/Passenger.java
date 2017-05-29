package p05_rollerCoaster;

import java.util.concurrent.atomic.AtomicBoolean;

public class Passenger extends Thread {
	
	private static int nextId=0;
	
	private int id;
	private AtomicBoolean running;
	private RollerCoasterMonitor monitor;
	
	public Passenger (AtomicBoolean running, RollerCoasterMonitor monitor) {
		this.running = running;
		this.monitor = monitor;
		this.id = nextId++;
	}
	
	public void run () {
		while (true) {
			pause(200);
			monitor.board();
			System.out.println("Passenger "+id+" has boarded the car");
			while (!running.get()) pause(10);
			while (running.get()) {
				System.out.println("    Passenger "+id+" yeeeeells!!!");
				pause(300);
			}
			pause(Math.round(200+1000*Math.random()));
			monitor.unboard();
			System.out.println("Passenger "+id+" has unboarded the car");
		}
	}
	
	private void pause (long time) {
		try{Thread.sleep(time);}catch(InterruptedException ie){}
	}
	
}

package p05_rollerCoaster;

import java.util.concurrent.atomic.AtomicBoolean;

public class Car extends Thread {
	
	private AtomicBoolean running;
	private RollerCoasterMonitor monitor;
	
	public Car (AtomicBoolean running, RollerCoasterMonitor monitor) {
		this.running = running;
		this.monitor = monitor;
	}
	
	public void run () {
		while (true) {
			pause(500);
			System.out.println("+START BOARDING");
			monitor.load();
			System.out.println("+BOARDING COMPLETED");
			System.out.println("+RIDE STARTS NOW");
			running.set(true);
			pause(2000);
			System.out.println("-RIDE IS ABOUT TO FINISH");
			running.set(false);
			System.out.println("-START UNBOARDING");
			monitor.unload();
			System.out.println("-UNBOARDING COMPLETED");
		}
	}
	
	private void pause (int time) {
		try{Thread.sleep(time);}catch(InterruptedException ie){}
	}
	
}

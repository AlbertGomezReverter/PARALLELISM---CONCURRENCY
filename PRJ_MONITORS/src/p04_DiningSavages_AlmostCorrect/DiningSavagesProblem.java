package p04_DiningSavages_AlmostCorrect;

public class DiningSavagesProblem {
	public static void main(String[] args) {

		DiningSavagesMonitor monitor = new DiningSavagesMonitor(4);
		Cook cook = new Cook(monitor);
		Savage tribe[] = new Savage[10];
		for (int i = 0; i < tribe.length; i++) {
			tribe[i] = new Savage(monitor);
			tribe[i].start();
		}
		cook.start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException ie) {
		}

		System.exit(0);
	}
}

class Savage extends Thread {

	private static int nextId = 1;

	private int id;
	private DiningSavagesMonitor monitor;

	public Savage(DiningSavagesMonitor monitor) {
		this.monitor = monitor;
		this.id = nextId;
		nextId++;
	}

	@Override
	public void run() {
		while (true) {
			monitor.helpYourself(id);
			try {
				sleep(1000);
			} catch (InterruptedException ie) {
			}
		}
	}
}

class Cook extends Thread {

	private DiningSavagesMonitor monitor;

	public Cook(DiningSavagesMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		while (true) {
			monitor.refill();
		}
	}
}

class DiningSavagesMonitor {
	private final int CAPACITY;
	private int servings;

	public DiningSavagesMonitor(int capacity) {
		CAPACITY = capacity;
		servings = CAPACITY;
	}

	public synchronized void helpYourself(int id) {
		while (servings == 0) {
			System.out.println("    -- Savage " + id + " finds the pot empty and tries to wake up the cook");
			notifyAll(); // try to awaken the cook
			try {
				wait();
			} catch (InterruptedException ie) {
			}
		}

		System.out.println("yum, yum (" + id + ")");
		servings--;
		notifyAll();
	}

	public synchronized void refill() {
		while (servings > 0)
			try {
				wait();
			} catch (InterruptedException ie) {
			}

		servings = CAPACITY;
		System.out.println("REFILLED!");
		notifyAll();
	}
}

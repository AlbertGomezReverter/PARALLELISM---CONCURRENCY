package B_exer_tic_tac_toe;

public class B_TicTacTacToe {

	public static void main(String[] args) {

		Shared shared = new Shared();
		Tic tic = new Tic(shared);
		Tac tac = new Tac(shared);
		Toe toe = new Toe(shared);

		tic.start();
		tac.start();
		toe.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
		}

		tic.stop();
		tac.stop();
		toe.stop();
	}
}

class Shared {
	private volatile int availableTICs = 1;
	private volatile int availableTACs = 0;
	private volatile int availableTOEs = 0;
	int time = 0;

	public void letMeTic() {
		// invoked by Tic before writing TIC-.
		// if this method returns then it is safe to write TIC-

		while (availableTICs != 1) {
			makeMeSleepForAWhile();
		}

	}

	public void ticWritten() {
		// invoked by Tic just after printing TIC-

		this.availableTICs = 0;
		this.availableTACs = 1;

	}

	public void letMeTac() {
		// invoked by Tac before writing TAC.
		// if this method returns then it is safe to write TAC

		while (availableTACs != 1) {
			makeMeSleepForAWhile();
		}

	}

	public void tacWritten() {
		// invoked by Tac just after printing TAC

		if (time >= 2) {
			this.availableTACs = 0;
			this.availableTOEs = 1;
			time = 0;
		}
		time++;
	}

	public void letMeToe() {
		// invoked by Toe before writing -TOE.
		// if this method returns then it is safe to write -TOE

		while (availableTOEs != 1) {
			makeMeSleepForAWhile();
		}

	}

	public void toeWritten() {
		// invoked by Toe just after printing -TOE

		this.availableTOEs = 0;
		this.availableTICs = 1;
	}

	// convenience method
	private void makeMeSleepForAWhile() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException ie) {
		}
	}

}

class Tic extends Thread {

	private Shared shared;

	public Tic(Shared sh) {
		this.shared = sh;
	}

	public void run() {
		while (true) {
			shared.letMeTic();
			// when here it is safe to write TIC
			System.out.print("TIC-");
			// tell the "world" TIC has already been written
			shared.ticWritten();
		}
	}
}

class Tac extends Thread {

	private Shared shared;
	private int times = 0;

	public Tac(Shared sh) {
		this.shared = sh;
	}

	public void run() {
		while (true) {
			shared.letMeTac();
			System.out.print("TAC");
			shared.tacWritten();
		}
	}
}

class Toe extends Thread {

	private Shared shared;

	public Toe(Shared sh) {
		this.shared = sh;
	}

	public void run() {
		while (true) {
			shared.letMeToe();
			System.out.println("-TOE");
			shared.toeWritten();
		}
	}
}

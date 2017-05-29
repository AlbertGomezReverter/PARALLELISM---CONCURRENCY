package A_exer_tic_tac_toe;

public class A_TicTacTacToe {

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
	/*
	 * ...an object that encapsulates three boolean attributes (they may be
	 * public) and nothing more
	 */
	private volatile boolean ticBool;
	private volatile boolean tacBool;
	private volatile boolean toeBool;

	public Shared() {
		ticBool = true;
		tacBool = false;
		toeBool = false;

	}

	public boolean isTicBool() {
		return ticBool;
	}

	public boolean isTacBool() {
		return tacBool;
	}

	public boolean isToeBool() {
		return toeBool;
	}

	public void setTicBool(boolean ticBool) {
		this.ticBool = ticBool;
		this.tacBool = true;
	}

	public void setTacBool(boolean tacBool) {
		this.tacBool = tacBool;
		this.toeBool = true;
	}

	public void setToeBool(boolean toeBool) {
		this.toeBool = toeBool;
		this.ticBool = true;
	}

}

class Tic extends Thread {
	// endlessly prints TIC-

	private Shared sharedObject;

	public Tic(Shared sh) {
		this.sharedObject = sh;
	}

	public void run() {
		while (true) {
			while (this.sharedObject.isTicBool() != true) {
				yield();
			}
			System.out.print("TIC-");
			sharedObject.setTicBool(false);
		}
	}

}

class Tac extends Thread {
	// endlessly prints TAC

	private Shared sharedObject;
	private int times = 0;

	public Tac(Shared sh) {
		this.sharedObject = sh;
	}

	public void run() {
		while (true) {
			while (this.sharedObject.isTacBool() != true) {
				yield();
			}
			System.out.print("TAC");
			if (this.times == 2) {
				sharedObject.setTacBool(false);
				this.times = 0;
			}
			times++;
		}
	}
}

class Toe extends Thread {
	// endlessly prints -TOE

	private Shared sharedObject;

	public Toe(Shared sh) {
		this.sharedObject = sh;
	}

	public void run() {
		while (true) {
			while (this.sharedObject.isToeBool() != true) {
				yield();
			}
			System.out.println("-TOE");
			sharedObject.setToeBool(false);
		}
	}

}

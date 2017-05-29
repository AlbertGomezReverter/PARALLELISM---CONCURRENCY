package p05_rollerCoaster;

public class RollerCoasterMonitor {
	
	private final int CAPACITY = 4;
	int onCar = 0;
	boolean openForBoarding, openForUnboarding = false;
	
	public synchronized void load () {
		openForBoarding = true;
		
		notifyAll(); // signalC(canBoard);
		
		while (onCar<CAPACITY) try{wait();} catch(InterruptedException ie){} // waitC(carIsFull)
		
		openForBoarding = false;
	}
	
	public synchronized void unload () {
		openForUnboarding = true;
		
		notifyAll(); // signalC(canUnboard);
		
		while (onCar>0) try{wait();} catch(InterruptedException ie){} // waitC(carIsEmpty)
		
		openForUnboarding = false;
	}
	
	public synchronized void board () {
		while (!openForBoarding || onCar==CAPACITY)
			try{wait();} catch(InterruptedException ie){} // waitC(canBoard)
		
		onCar++;
		
		/*
		if (onCar==CAPACITY) 
			notifyAll(); // signalC(carIsFull) -- wake up the car
		else
			notifyAll(); //signalC(canBoard) -- cascade awakening of other passengers
		 */
		notifyAll(); 
	}
	
	public synchronized void unboard () {
		while (!openForUnboarding)
			try{wait();} catch(InterruptedException ie){} // waitC(canUnboard)
		
		onCar--;
		
		/*
		if (onCar==0) 
			notifyAll(); // signalC(carIsEmpty) -- wake up the car
		else
			notifyAll(); //signalC(canUnboard) -- cascade awakening of other passengers
		 */
		notifyAll(); 
	}

}

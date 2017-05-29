package p02_CarPark;

import java.io.*;
import java.net.*;

public class CarParkClient {

	public static void main(String[] args) throws IOException {
		Car[] fleet = new Car[10];
		for (int i = 0; i < fleet.length; i++) {
			fleet[i] = new Car();
			fleet[i].start();
		}
	}
}

class Car extends Thread {

	public static int ENTER = 0, EXIT = 1;

	private static final long DRIVE = 1;
	private static final long PARK = 5;

	private int id;
	private static int nextId = 1;

	private Socket connection;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;

	public Car() {
		this.id = nextId;
		nextId++;
	}

	@Override
	public void run() {
		try {
			innerRun();
		} catch (IOException ioex) {
			ioex.printStackTrace(System.err);
		}
	}

	public void innerRun() throws IOException {

		long drivingTime = (long) (Math.random() * DRIVE * 1000);
		long parkingTime = (long) (Math.random() * PARK * 1000);

		while (true) {
			// drive around for a while
			try {
				Thread.sleep(drivingTime);
			} catch (InterruptedException ie) {
			}

			System.out.println("Car " + id + " requests access to the car park");
			// try to park...
			connect();
			sendRequest(ENTER);
			receiveReply();
			disconnect();

			System.out.println("Car " + id + " is IN the car park");
			// now stay in the parking lot for a while
			try {
				Thread.sleep(parkingTime);
			} catch (InterruptedException ie) {
			}

			// exit the car park
			connect();
			sendRequest(EXIT);
			disconnect(); // no need to wait for an answer

			System.out.println("Car " + id + " has EXITED the car park");
		}
	}

	private void connect() throws IOException {
		this.connection = new Socket("localhost", 4444);
		inputChannel = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		outputChannel = new PrintWriter(connection.getOutputStream(), true);
	}

	private void disconnect() throws IOException {
		this.inputChannel.close();
		this.outputChannel.close();
		this.connection.close();
	}

	private void receiveReply() throws IOException {
		this.inputChannel.readLine();
	}

	private void sendRequest(int request) throws IOException {
		this.outputChannel.println(request);
	}

}

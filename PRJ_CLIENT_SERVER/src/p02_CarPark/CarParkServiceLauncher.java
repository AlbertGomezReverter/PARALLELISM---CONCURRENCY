package p02_CarPark;

import java.io.*;
import java.net.*;
import java.util.*;

public class CarParkServiceLauncher {
	public static void main(String[] args) {
		// launches a single instance of the server
		new CarParkServer().start();
	}

}

class CarParkServer extends Thread {

	private final int CAPACITY = 4;
	public final int ENTER = 0, EXIT = 1;

	Queue<ClientRep> waiting = new ArrayDeque<ClientRep>();
	// queue waiting is going to "implement" the notFull condition

	private int numCars = 0;
	private ServerSocket serverSocket;
	private Socket connection;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;

	@Override
	public void run() {
		try {
			innerRun();
		} catch (IOException ioex) {
			ioex.printStackTrace(System.err);
		}
	}

	public void innerRun() throws IOException {

		serverSocket = new ServerSocket(4444);
		System.out.println("Server running and listening to port 4444");

		while (true) {
			acceptConnection(); // bind to a particular client
			int request = receiveRequest(); // read the request

			// process the request...
			switch (request) {
			case ENTER:
				if (numCars < CAPACITY) {
					numCars++;
					sendReply();
					disconnect();
				} else {
					// client has to WAIT. Store (enqueue) the
					// connection but DO NOT close it
					waiting.add(new ClientRep(connection, inputChannel, outputChannel));
				}
				break;

			case EXIT:
				disconnect();
				if (!waiting.isEmpty()) {
					// SIGNAL!
					restoreConnection(waiting.remove());
					sendReply();
					disconnect();
				} else {
					numCars--;
				}
			} // end of switch
		} // end of main (endless) loop
	}

	private int receiveRequest() throws IOException {
		return Integer.parseInt(this.inputChannel.readLine());
	}

	private void sendReply() throws IOException {
		this.outputChannel.println("Enter");
	}

	private void acceptConnection() throws IOException {
		this.connection = this.serverSocket.accept();
		this.inputChannel = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
		this.outputChannel = new PrintWriter(this.connection.getOutputStream(), true);
	}

	private void disconnect() throws IOException {
		this.connection.close();
		this.inputChannel.close();
		this.outputChannel.close();
	}

	private void restoreConnection(ClientRep cr) {
		this.connection = cr.connection;
		this.inputChannel = cr.inputChannel;
		this.outputChannel = cr.outputChannel;
	}

}

class ClientRep {
	// utility class to store a connection and its channels
	public Socket connection;
	public BufferedReader inputChannel;
	public PrintWriter outputChannel;

	public ClientRep(Socket co, BufferedReader iCh, PrintWriter oCh) {
		this.connection = co;
		this.inputChannel = iCh;
		this.outputChannel = oCh;
	}
}

class cocacola {
}
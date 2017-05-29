package p03_SimpleCurrentAccountService;

import java.io.*;
import java.net.*;
import Keyboard.*;

public class CurrentAccountClient extends Thread {
    
    public static final String WITHDRAW = "WITHDRAW";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String GET_BALANCE = "GET_BALANCE";
    
    private String operation;
    private float amount;
    private String result = "UNKNOWN";
    
    private Socket connection;
    private BufferedReader inputChannel;
    private PrintWriter outputChannel;

    
    public CurrentAccountClient (String operation, float amount) {
        this.operation = operation;
        this.amount = amount;
    }
    
    public String getResult() {
        return this.result;
    }
    
    @Override
    public void run () {
        try {innerRun();}
        catch (IOException ioex) {ioex.printStackTrace(System.err);} 
    }
    
    public void innerRun () throws IOException {
        String request = operation+" "+amount;
        connect();
        sendRequest(request);
        result = receiveReply();
        disconnect();
    }
    
    
    private void connect () throws IOException {
        this.connection = new Socket("localhost", 5555);
        inputChannel = new BufferedReader(
                           new InputStreamReader(
                               connection.getInputStream()));
        outputChannel = new PrintWriter(connection.getOutputStream(), true);
    }
    
    private void disconnect () throws IOException {
        this.inputChannel.close();
        this.outputChannel.close();       
        this.connection.close();
    }
    
    private String receiveReply () throws IOException {
        return this.inputChannel.readLine();    
    }
    
    private void sendRequest (String request) throws IOException {
        this.outputChannel.println(request);
    }
    
    // launcher
    public static void main (String [] args) {
        
        int option;
        float amount = 0;
        CurrentAccountClient client = null;
        
        System.out.println("WELCOME to the simplest ATM in the world");
        System.out.println("   Enter 1 to WITHDRAW MONEY");
        System.out.println("   Enter 2 to DEPOSIT MONEY");
        System.out.println("   Enter 3 to KNOW YOUR BALANCE");
        System.out.print("option? ");
        option = Keyboard.readInt();
        if (option<1 || option>3) {
            System.out.println("bad option. Bye");
            System.exit(1);
        }
        
        if (option==1 || option==2) {
            System.out.print("amount? ");
            amount = Keyboard.readFloat();
        }
        
        switch (option) {
            case 1:  client = new CurrentAccountClient(WITHDRAW, amount);
                     break;
            case 2: client = new CurrentAccountClient(DEPOSIT, amount);
                    break;
            case 3: client = new CurrentAccountClient(GET_BALANCE, 0);
        }
        
        client.start();
        try {client.join();} catch(InterruptedException ie) {}
        
        System.out.println("According to our server, this is the result of your"
                + " operation: "+client.getResult());     
    }
}



package p03_SimpleCurrentAccountService;

import java.io.*;
import java.net.*;

public class SimpleCurrentAccountService {
    public static void main (String [] args) {
        new CA_Server().start();
    }
}

class CA_Server extends Thread {
    
    private ServerSocket serverSocket;
    private Socket connection;
    private BufferedReader inputChannel;
    private PrintWriter outputChannel;
    
    private float balance = 0;
    
    @Override
    public void run () {
        try {innerRun();}
        catch(IOException ioex) 
             {ioex.printStackTrace(System.err);}
    }
    
    public void innerRun () throws IOException {
        
        Request request;
        String answer = "";
        
        serverSocket = new ServerSocket(5555);
        System.out.println("Server running and listening to port 5555");
        
        while (true) {
            acceptConnection();
            request = receiveRequest();
            switch (request.type) {
                case Request.WITHDRAW:
                    if (request.amount > balance) {answer = "NOT_POSSIBLE";}
                    else {
                        balance = balance-request.amount;
                        answer = "DONE";
                    }
                    break;
                case Request.DEPOSIT:
                    balance = balance + request.amount;
                    answer = "DONE";
                    break;
                case Request.GET_BALANCE:
                    answer = Float.toString(balance);
            }
            sendReply(answer);
            disconnect();
        }
    }
    
    private Request receiveRequest () throws IOException {
        String message = this.inputChannel.readLine();
        String [] elements = message.split(" ");
        if (elements[0].equals("WITHDRAW")) {
            return new Request(Request.WITHDRAW, 
                               Float.parseFloat(elements[1]));
        }
        if (elements[0].equals("DEPOSIT")) {
            return new Request(Request.DEPOSIT, 
                               Float.parseFloat(elements[1]));
        }
        return new Request(Request.GET_BALANCE, 0);
    }
    
    private void sendReply (String message) throws IOException {
        this.outputChannel.println(message);
    }
    
    private void acceptConnection () throws IOException {
        this.connection = this.serverSocket.accept();
        this.inputChannel = new BufferedReader(
                                new InputStreamReader(
                                    this.connection.getInputStream()));
        this.outputChannel = new PrintWriter(
                                 this.connection.getOutputStream(), true);
    }
    
    private void disconnect () throws IOException {
        this.connection.close();
        this.inputChannel.close();
        this.outputChannel.close();
    }
     
}

class Request {
    // convenience class to store information 
    // from a request sent to the server
    public static final int WITHDRAW = 0;
    public static final int DEPOSIT = 1;
    public static final int GET_BALANCE = 2;
    
    public int type;
    public float amount;
    
    public Request (int type, float amount) {
        this.type = type;
        this.amount = amount;
    }
}
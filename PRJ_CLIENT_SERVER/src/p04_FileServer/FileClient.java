package p04_FileServer;

import java.io.*;
import java.net.*;

public class FileClient extends Thread {
    
    Socket connection;
    BufferedReader inputChannel;
    PrintWriter outputChannel;
    
    private String fileName;
    
    public FileClient (String fileName) {
        this.fileName = fileName;
    }
    
    public void run () {
        try {innerRun();}
        catch(IOException ioex) {ioex.printStackTrace(System.err);}
    }
    
    public void innerRun () throws IOException {
       
        String answer;
        String line;
    
        connect();
        // send filename and receive the answer
        send(fileName);
        answer = receive();
        
        if (answer.equals("WRONG FILENAME")) {
            System.err.println("ups! wrong filename");
        } 
        else { // file is ready to be accessed...
            line = receive();
            while (!line.equals("\u001a")) {
                System.out.println(line);
                line = receive();
            }
            System.out.println("\n***** END OF FILE REACHED *******");
        }

        // tell the server connection is going to be closed...
        send("BYE BYE");
        disconnect();
    }
    
    private  String receive () throws IOException{
        return this.inputChannel.readLine();
    }
    
    private  void send (String message) throws IOException {
        this.outputChannel.println(message);
    } 
    
    private void connect () throws IOException {
        this.connection = new Socket("localhost", 4445);
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
    
    public static void main(String [] args) {
        FileClient fc = new FileClient("morning.txt");
        fc.start();
    }
    
}
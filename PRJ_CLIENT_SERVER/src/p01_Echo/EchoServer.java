package p01_Echo;

import java.net.*;
import java.io.*;

public class EchoServer {

    public static void main (String [] args) {
        
        int numServices = 1;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader inputChannel = null;
        PrintWriter outputChannel = null;
        
        
        try {
            serverSocket = new ServerSocket(4444);
        }
        catch(IOException ioex) {
            System.err.println("cannot listen to port 4444");
            System.err.println(ioex);
            System.err.println("Server NOT Started");
            System.exit(1);
        }
        
        System.out.println("Server STARTED, now listening to port 4444");
        
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            }
            catch(IOException ioex) {
                System.err.println("accept failed");
                System.err.println(ioex);
                System.out.println("Server shutting down");
                System.exit(1);
            }
            
            // get the streams associated with the client socket
            try {
                inputChannel = new BufferedReader(
                                   new InputStreamReader(
                                       clientSocket.getInputStream()
                                   )
                               );
                outputChannel = new PrintWriter(
                                    clientSocket.getOutputStream(), true
                                );
            }
            catch(IOException ioex) {
                System.err.println("channels unavailable");
                System.err.println(ioex);
                System.out.println("Server shutting down");
                System.exit(1);
            }
            
            try {
                // read (= request)
                String line = inputChannel.readLine();
               
                // server can take its time...
                try {Thread.sleep(1000);} catch(InterruptedException ie) {}
                
                // write (echo) (= response)
                outputChannel.println("ECHO("+numServices+"): "+line);
                
                numServices++;
                
                // close connection with client
                inputChannel.close();
                outputChannel.close();
                clientSocket.close();
                
            } catch (IOException ioex) {}
            
        }
    }
}

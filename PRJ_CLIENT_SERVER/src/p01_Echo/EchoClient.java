package p01_Echo;

import java.io.*;
import java.net.*;

public class EchoClient {
    
    public static void main (String [] args) {
        
        Socket echoService = null;
        BufferedReader inputChannel = null;
        PrintWriter outputChannel = null;
        
        try {
            echoService = new Socket("localhost", 4444);
        }
        catch(UnknownHostException uhex) {
            System.err.println("bad host...");
            System.err.println(uhex);
            System.err.println("client cannot continue");
            System.exit(1);
        }
        catch(IOException ioex) {
            System.err.println("something went wrong while connecting to server");
            System.err.println(ioex);
            System.err.println("client cannot continue");
            System.exit(1);
        }
        
        System.out.println("Connection with server established");
        
        // get the channels
        try {
            inputChannel = new BufferedReader(
                    new InputStreamReader(
                    echoService.getInputStream()));
            outputChannel = new PrintWriter(
                    echoService.getOutputStream(), true);
        } catch (IOException ioex) {
            System.err.println("channels unavailable");
            System.err.println(ioex);
            System.out.println("client cannot continue");
            System.exit(1);
        }
        
        //request the service and wait for the answer...
        try {
            outputChannel.println("Requesting echo to echoServer. Please answer");
            // try {Thread.sleep(10000);} catch(InterruptedException ie) {}
            String answer = inputChannel.readLine();
            System.out.println("Server says: ");
            System.out.println("   "+answer);
            
            // close whatever needs to be closed...
            outputChannel.close();
            inputChannel.close();
            echoService.close();
        }
        catch(IOException ioex) {}
    }
    
}

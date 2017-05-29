package p01_Echo_With_Exception;

import java.io.*;
import java.net.*;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        Socket connection = null;
        BufferedReader inputChannel = null; // the reply channel
        PrintWriter outputChannel = null; // the request channel

        // request connection with server
        connection = new Socket("localhost", 4444);
        System.out.println("Connection with server established");

        // get the channels from the connection
        inputChannel = new BufferedReader(
                           new InputStreamReader(
                               connection.getInputStream()));
        outputChannel = new PrintWriter(connection.getOutputStream(), true);


        //request the service...
        outputChannel.println("Hello, hello, is anybody there?");
        
        // ... and wait for the answer
        String answer = inputChannel.readLine();
        
        System.out.println("Server says: ");
        System.out.println("   " + answer);

        // close connection and channels
        outputChannel.close();
        inputChannel.close();
        connection.close();
    }
}

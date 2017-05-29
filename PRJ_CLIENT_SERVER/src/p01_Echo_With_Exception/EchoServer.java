package p01_Echo_With_Exception;

import java.net.*;
import java.io.*;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        
        int numServices = 1;

        ServerSocket serverSocket;
        Socket connection;
        BufferedReader inputChannel; // the request channel
        PrintWriter outputChannel; // the reply channel

        serverSocket = new ServerSocket(4444);
        System.out.println("Server STARTED, now listening to port 4444");

        while (true) {
            // accept a new connection
            connection = serverSocket.accept();

            // get the channels from the connection
            inputChannel = new BufferedReader( 
                               new InputStreamReader(
                                   connection.getInputStream()));
            outputChannel = new PrintWriter(connection.getOutputStream(),true);

            // read (= receive request)
            String line = inputChannel.readLine();
            // server can take its time...
            try {Thread.sleep(1000);} catch (InterruptedException ie) {}

            // write (echo) (= send response)
            outputChannel.println("ECHO(" + numServices + "): " + line);

            numServices++;

            // close connection with client
            inputChannel.close();
            outputChannel.close();
            connection.close();
        }
    }
}

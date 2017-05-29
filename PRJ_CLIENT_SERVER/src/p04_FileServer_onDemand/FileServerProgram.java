package p04_FileServer_onDemand;

import java.io.*;
import java.net.*;

public class FileServerProgram {
    
    public static void main (String [] args) throws IOException {
        
        Socket connection;
        ServerSocket serverSocket = new ServerSocket(4445);
        System.out.println("Accepting incoming connections on port 4445");
        
        while (true) {
            connection = serverSocket.accept();
            // launch a new FileServer
            new FileServer(connection).start();
        }
        
    }
}


class FileServer extends Thread {
   
    private Socket connection;
    private BufferedReader inputChannel;
    private PrintWriter outputChannel;
    
    public FileServer (Socket connection) {
        this.connection = connection;
    }
    
    public void run () {
        try {innerRun();}
        catch(IOException ioex) {ioex.printStackTrace(System.err);}
    }
    
    public void innerRun () throws IOException {
        String fileName;
        String line;
        BufferedReader bur = null;
        boolean openOK;
        
        setChannels();
        // receive the filename
        fileName = receive();
        // try to open the requested file
        openOK = false;
        try {
            bur = new BufferedReader(new FileReader(fileName));
            openOK = true;
        } catch (FileNotFoundException fnfe) {}

        if (!openOK) {
            send("WRONG FILENAME");
        } else { // acces the file and sent it line by line
            send("FILE OPEN");
            line = bur.readLine();
            while (line != null) {
                send(line);
                line = bur.readLine();
            }
            send("\u001a"); // \u001a is EOF
            bur.close();
        }
        // don't stop connection until the client says so...
        receive();
        disconnect();
    }
    
    private String receive() throws IOException {
        return this.inputChannel.readLine();
    }
    
    private void send( String message) throws IOException {
        this.outputChannel.println(message);
    }
    
    private void setChannels () throws IOException {
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



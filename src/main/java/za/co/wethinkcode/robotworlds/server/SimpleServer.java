package za.co.wethinkcode.robotworlds.server;

import java.io.*;
import java.net.*;

//	Our SimpleServer class implements Runnable, which is an interface that makes it possible to
//	create a new Thread and give it an instance of this class. It will know to invoke the run method.
public class SimpleServer implements Runnable {

//  	We define our default port as a constant. For a real-world application
//  	you will rather read the port information from configuration files.
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;

//    Our constructor throws an IOException when something goes wrong in setting up the client
//    connection. This means constructing SimpleServer fails if it cannot connect properly to the
//    client. We like this, as it means we won’t have SimpleServer instances that are actually not connected.
    public SimpleServer(Socket socket) throws IOException {

//        	The Socket class can provide us with all kinds of information. Here we
//        	get the IP address of the client machine that connected to our server.
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

//        The socket.getOutputStream() will be used to send replies to the client.
        out = new PrintStream(socket.getOutputStream());

//        The socket.getInputStream() is used to receive messages from the client.
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Waiting for client...");
    }

    public void run() {
        try {
            String messageFromClient;

//            The in.readLine() call will block execution, i.e. the code will wait there, until there
//            is a string value available (or until it times out, in which case it will return null).
//            Here we read this string, print it, and send a response to the client.
            while((messageFromClient = in.readLine()) != null) {
                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine); // print message from client
                out.println("Thanks for this message: "+messageFromClient); // send message to client
            }
        }
        catch(IOException ex) {
            System.out.println("Shutting down single client server");
        }
        finally {
//    	We call the closeQuietly() method when shutting down the server. It will just try to close
//    	the relevant input and output streams, and hide any error happening while doing so.
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }
}
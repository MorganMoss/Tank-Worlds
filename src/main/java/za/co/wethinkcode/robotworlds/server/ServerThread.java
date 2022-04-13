package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


/**
 * A thread that allows 2-way communication 
 * between a client and the server.
 */
public class ServerThread extends Thread{
    /**
     * The server object this thread belongs to
     */
    private final Server server;

    /**
     * The client's requests are received from this
     */
    private final BufferedReader in;

    /**
     * The responses are sent to the client from this
     */
    private final PrintStream out;

    /**
     * The client's name
     */
    private final String clientMachine;

    /**
     * The client's number on the server
     */
    private final int clientNumber;

    /**
     * Makes a new thread for the server for a client that has just connected.
     * It will allow for communication between a client and the server when run
     * @param server : the server this is run from
     * @param socket : the socket used to connect the client to the server
     * @param clientNumber : the number the client is assigned
     * @throws IOException : the connection failed
     */
    public ServerThread(Server server, Socket socket, int clientNumber) throws IOException{
        this.server = server;
        this.clientNumber = clientNumber;
        
        clientMachine = socket.getInetAddress().getHostName();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());

        System.out.println("Connection from " + clientMachine);
    }

    /**
     * Waits for a request from client, gives it to the server
     * Then will wait for server to respond and sends that response to the client
     */
    @Override
    public void run() {
        Responder responder = new Responder(server, out, clientNumber);
        responder.start();
        
        Requester requester = new Requester(server, in, clientNumber);
        requester.start();

        while (requester.isAlive() && responder.isAlive()) {}
        
        closeQuietly();
    }
        
    /**
     * Closes the communication without raising errors
     */
    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }
}

/**
 * The thread used to send responses from the server to the client
 */
class Responder extends Thread{
    private final int clientNumber;
    private final Server server;
    private final PrintStream out;

    public Responder(Server server, PrintStream out, int number) {
        clientNumber = number;
        this.server = server;
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            try {
                out.println(server.getResponse(clientNumber));
            } catch (NoChangeException ignored) {}
        }
    }
}

/**
 * The thread used to get requests from the client and give them to the server
 */
class Requester extends Thread{
    private final int clientNumber;
    private final Server server;
    private final BufferedReader in;

    public Requester(Server server, BufferedReader in, int number) {
        clientNumber = number;
        this.server = server;
        this.in = in;
    }

    @Override
    public void run() {
        String request;
        try {
            while((request = in.readLine()) != null){
                server.addRequest(clientNumber, Request.deSerialize(request));
            }
        }
        catch(IOException ex) {
            System.out.println("Shutting down client");
        }
    }
}
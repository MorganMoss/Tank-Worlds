package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

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
     * Makes a new thread for the server for a client that has just connected.
     * It will allow for communication between a client and the server when run
     * @param server : the server this is run from
     * @param socket : the socket used to connect the client to the server
     * @throws IOException : the connection failed
     */
    public ServerThread(Server server, Socket socket) throws IOException{
        this.server = server;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());

        System.out.println("Connection from " + socket.getInetAddress().getHostName());
    }

    /**
     * Waits for a request from client, gives it to the server
     * Then will wait for server to respond and sends that response to the client
     */
    @Override
    public void run() {
        String request;
        try {
            while((request = in.readLine()) != null){
                Response response = server.executeRequest(Request.deSerialize(request));
                out.println(response.serialize());
            }
        } catch(IOException ex) {
                System.out.println("Shutting down client");
        }

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

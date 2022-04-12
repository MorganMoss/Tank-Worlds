package za.co.wethinkcode.robotworlds.server;

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
    // private final Socket socket;
    private final Server server;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private final int number;

    /**
     * Makes a new thread for the server for a client that has just connected.
     * It will allow for communication between a client and the server when run
     * @param server : the server this is run from
     * @param socket : the socket used to connect the client to the server
     * @param clientNumber : the number the client is assigned
     * @throws IOException
     */
    public ServerThread(Server server, Socket socket, int clientNumber) throws IOException{
        number = clientNumber;
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);
        // this.socket = socket;
        this.server = server;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());
        System.out.println("Waiting for client...");
    }

    /**
     * Waits for a request from client, gives it to the server
     * Then will wait for server to respond and sends that response to the client
     */
    @Override
    public void run() {
        try {
            String messageFromClient;
                while((messageFromClient = in.readLine()) != null) {
                    server.addRequest(number, messageFromClient);
                    while (true) {
                        try 
                        {
                            out.println(server.getResponse(number));   
                            break;
                        } catch (NoChangeException nce) {
                            //waiting for response
                        }
                    }
                }
            }
            catch(IOException ex) {
                System.out.println("Shutting down single client server");
            }
            finally {
                closeQuietly();
            }
    }
        
    /**
     * Closes the communication without raising errors
     */
    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }
}
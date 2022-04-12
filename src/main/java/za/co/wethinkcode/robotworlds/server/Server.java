package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import java.net.*;
import java.io.*;
import java.util.HashMap;

/**
 * The server that will be run. Clients will connect to it. 
 * It has 2-way communication and support for many clients
 */
public class Server implements Runnable{
    /**
     * The world the server interacts with when handling requests and responses
     */
    private final World world;

    /**
     * The socket clients will connect to
     */
    private final ServerSocket socket;

    /**
     * The list of requests that will be run in the next server tick
     */
    private HashMap<Integer, String> currentRequests;

    /**
     * The list of responses that will be sent in the next server tick
     */
    private HashMap<Integer, String> currentResponses;

    /**
     * Used to indicate the number of clients connected to the server,
     * as well as to give a new client a number.
     */
    public int clientCount = 0;

    /**
     * Makes an instance of a server. It uses the port given for the server socket
     * @param port : the port clients will use to connect to the server
     * @throws IOException
     */
    private Server(int port) throws IOException{
        this.socket = new ServerSocket(port);
        world = new World(getMap(), getRepairTime());
        currentRequests = new HashMap<>();
        currentResponses = new HashMap<>();
    }

    /**
     * This should take the config file and get the repair time
     * @return the number of seconds it takes to repair a robot's armour
     */
    private int getRepairTime() {
        return 3;
    }

    /**
     * This should take the config file and get a map
     * @return a map that will be used to define the world's size and it's obstacles
     */
    private Map getMap() {
        Map map = new BasicMap();
        return map;
    }

    /**
     * Should execute all requests and create a new response for each client
     * It should clear the list as it goes
     */
    private void executeRequests() {
        if (clientCount == 0) {
            return;
        }
        for (int client = 1; client <= clientCount; client++) {
            String request = currentRequests.get(client);
            if (request == null){
                System.out.println(client + ": idle");
            } else {
                System.out.println(client + ": " + request);
                currentResponses.put(client, "We got your message, number " + client + " : " + request);
            }
            currentRequests.remove(client);
        }
    }
    
    /**
     * Looks for a response from the server to give the client.
     * Used by a server thread.
     * @param client : the client looking for a response
     * @return a formatted response
     */
    public String getResponse(int client){
        String response = currentResponses.get(client);
        if (response == null) {
            throw new NoChangeException();
        }
        currentResponses.remove(client);
        return response;
    }

    /**
     * Allows a server thread to give the server a request for a client
     * @param client : the client giving the request
     * @param request : the request the client is giving
     */
    public void addRequest(int client, String request){
        if (currentRequests.get(client) == null){
            currentRequests.put(client, request);
        }
    }


    /**
     * The server is run from here. 
     * @param args
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        final int port = 5000;
        Server server = new Server(port);

        System.out.println("Server running & waiting for client connections.");

        Thread serverLoop = new Thread(server);
        serverLoop.start();

        while(true) {
            try {
                Socket socket = server.socket.accept();
                server.clientCount += 1;
                ServerThread serverThread = new ServerThread(server, socket, server.clientCount);
                serverThread.start();
                System.out.println("A client has been connected. Their number is : " + server.clientCount);
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        do {
            System.out.println("Loop Running");
            this.executeRequests();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        } while (true);
    }


}

class NoChangeException extends RuntimeException{
}
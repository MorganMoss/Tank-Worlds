package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.command.Command;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.net.*;
import java.io.*;
import java.util.HashMap;

/**
 * The server that will be run. Clients will connect to it. 
 * It has 2-way communication and support for many clients
 */
public class Server implements Runnable{
    /**
     * The amount of milliseconds to wait before the next tick
     */
    private static int tickInterval = 50;

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
    private final HashMap<Integer, Request> currentRequests;

    /**
     * The list of responses that will be sent in the next server tick
     */
    private final HashMap<Integer, Response> currentResponses;

    /**
     * Used to indicate the number of clients connected to the server,
     * as well as to give a new client a number.
     */
    public int clientCount = 0;

    /**
     * Makes an instance of a server. It uses the port given for the server socket
     * @param port : the port clients will use to connect to the server
     * @throws IOException : throws when server socket fails
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
        return new BasicMap();
    }

    public World getWorld() {
        return world;
    }

    /**
     * Should execute all requests and create a new response for each client
     * It should clear the list as it goes
     */
    private void executeRequests() {
        if (clientCount == 0) {
            return;
        }
        for (int client : currentRequests.keySet()) {
            Request request = currentRequests.get(client);
            if (request == null){
                System.out.println(client + ": idle");
            } else {
                try {
                    Command command = Command.create(request);
//                    for (Robot robot : world.getRobots()) {
//                        if (robot.getName() == client) {
//                            command.execute(robot);
//                        }
//                    }
                } catch (IllegalArgumentException e) {
                    currentResponses.putIfAbsent(client, new Response("robot " + client, "Command not found"));
                }
                System.out.println(client + ": " + request.toString());
            }
            //TODO properly. it's just sending back the request, should be a general info about robot and surroundings
            currentResponses.putIfAbsent(client, new Response("robot " + client, request.serialize()));
            currentRequests.remove(client);
        }
    }
    
    /**
     * Looks for a response from the server to give the client.
     * Used by a server thread.
     * @param client : the client looking for a response
     * @return a formatted response object
     */
    public Response getResponse(int client){
        Response response = currentResponses.get(client);
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
    public void addRequest(int client, Request request){
        currentRequests.putIfAbsent(client, request);
    }

    /**
     * Starts up the server
     * @throws IOException : raised when server object fails
     */
    public static void start() throws IOException {
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

    /**
     * The server is run from here. 
     * @param args : none are applicable
     * @throws IOException : raised when server object fails
     */
    public static void main(String[] args) throws IOException {
        start();
    }

    /**
     * Executes current requests and makes responses for all the clients every time interval
     * when run on a separate thread
     */
    @Override
    public void run() {
        do {
            this.executeRequests();
            try {
                Thread.sleep(tickInterval);
            } catch (InterruptedException ignored) {
            }
        } while (true);
    }
}

/**
 * Raised when there is no new response from the server for a specific client
 */
class NoChangeException extends RuntimeException{
}
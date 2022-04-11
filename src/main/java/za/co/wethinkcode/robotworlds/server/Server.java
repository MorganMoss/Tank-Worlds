package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.*;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import java.net.*;
import java.io.*;
import java.util.List;

/**
 * The server that will be run. Clients will connect to it
 */
public class Server {
    /**
     * The world the server interacts with when handling requests and responses
     */
//    private final World world = new World(getMap(), getRepairTime());

    /**
     * The socket clients will connect to
     */
    private ServerSocket socket;

    /**
     * The list of requests that will be run in the next server tick
     */
    private List<Request> currentRequests;

    /**
     * The list of responses that will be sent in the next server tick
     */
    private List<Response> currentResponses;

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
        for (Request request : currentRequests) {
            currentRequests.remove(request);
        }
    }
    
    /**
     * Should send all responses to the respective clients
     * It should clear the list as it goes
     */
    private void sendResponses() {
        for (Response response : currentResponses) {
            currentResponses.remove(response);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

//        We use the PORT as defined in SimpleServer. This is the port that client
//        applications must connect to. ServerSocket is used on the server side to
//        manage client connections.
        ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);
        System.out.println("Server running & waiting for client connections.");

        while(true) {
            try {
//        The accept() method blocks execution (i.e. it waits) until a client has
//        connected, then it returns an instance of Socket that represents the
//        connection with that specific client.
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);

//        We create an instance of SimpleServer that will handle the
//        communications with the specific client that has connected.
//        We then create a Thread that will let our SimpleServer instance
//        run in the background, i.e. not affecting the code in this main
//        method, or other client connections
                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

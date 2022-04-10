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
//    private final World world;


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
     * Opens a server socket and initializes world from config.
     */
//    public Server() {
//        try {
//            socket = new ServerSocket();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        world = new World(getMap(), getRepairTime());
//    }


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

    /**
     * should execute requests and send reponses to clients when run
     * @param args : no args are handled
     */
//    public static void main(String[] args) {
//        Server server = new Server();
//        do {
//            server.executeRequests();
//            server.sendResponses();
//        } while (true);
//    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        ServerSocket s = new ServerSocket(SimpleServer.PORT);
        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new SimpleServer(socket);
                Thread task = new Thread(r);
                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.exceptions.NoChangeException;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.command.Command;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.net.*;
import java.io.*;
import java.util.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The server that will be run. Clients will connect to it. 
 * It has 2-way communication and support for many clients
 */
public class Server implements Runnable {

    private static int tickInterval = 50;
    /**
     * The world the server interacts with when handling requests and responses
     */
    private final World world;
    /**
     * The socket clients will connect to
     */
    private final ServerSocket socket;
    //TODO : Perhaps unify these into a list of Strings (i.e. Serialize them as you store them)

    /**
     * The list of requests that will be run in the next server tick
     */
    private final HashMap<Integer, Request> currentRequests;

    /**
     * The list of responses that will be sent in the next server tick
     */
    private final HashMap<Integer, Response> currentResponses;

    /**
     * Stores history of requests made to the server
     */
    private List<Request> requestLog;

    /**
     * Stores history of responses made to the server
     */
    private List<Response> responseLog;

    private int clientCount;

    /**
     * Makes an instance of a server. It uses the port given for the server socket
     * @param port : the port clients will use to connect to the server
     * @throws IOException : throws when server socket fails
     */
    private Server(int port) throws IOException{
        this.requestLog = new ArrayList<>();
        this.responseLog = new ArrayList<>();
        this.socket = new ServerSocket(port);
        this.clientCount = 0;
        this.world = new World(getMap());
        this.currentRequests = new HashMap<>();
        this.currentResponses = new HashMap<>();

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
        //TODO : Get the map to be used from the config file;
        // Size for a map should be determined by the map, not the server.
        return new BasicMap(new Position(600,600));
    }

    /**
     * Starts up the server
     * @throws IOException : raised when server object fails
     */
    public static void start() throws IOException {
        final int port = 5000;
        Server server = new Server(port);

        System.out.println("Server running & waiting for client connections.");

        while(true) {
            //TODO : Setup a separate input thread, so that commands like 'quit', 'dump' and 'robots' can be handled in the main loop
            try {
                Thread executeThread = new Thread(server);
                executeThread.start();

                Thread inputThread = new InputThread(server);
                inputThread.start();

                Socket socket = server.socket.accept();
                server.clientCount++;
                String socketName = socket.getLocalAddress().toString();
                System.out.println(socketName);

                ServerThread serverThread = new ServerThread(server, socket, server.clientCount);
                serverThread.start();
                System.out.println("A client has been connected. Their name is : " + socket.getInetAddress().getHostName());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

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

    /**
     * Allows a server thread to give the server a request for a client
     * @param client : the client giving the request
     * @param request : the request the client is giving
     */
    public void addRequest(int client, Request request){
        currentRequests.putIfAbsent(client, request);
    }

    /**
     * Should execute all requests and create a new response for each client
     * It should clear the list as it goes
     */
    private void executeRequests() {
        if (currentRequests.keySet().size() == 0) {
            return;
        }
        for (int client : currentRequests.keySet()) {
            Request request = currentRequests.get(client);
            String commandResponse = "";
            if (request == null){
                // do something
            } else try {
                this.requestLog.add(request);
                if (!Objects.equals(request.getCommand(), "idle")) {
                    System.out.println(request.serialize()); // PRINT REQUEST
                }
                Command command = Command.create(request);
                commandResponse = command.execute(world);
            } catch (IllegalArgumentException e) {
                commandResponse = "failed! bad input";
            }

            //TODO : Add all requests to a buffer before adding them to current requests (to prevent multiple responses per request)

            //TODO : use the look command on each robot to get the grid of values it

            //TODO properly. it's just sending back the request, should be a general info about robot and surroundings

            currentResponses.putIfAbsent(client, generateResponse(request, commandResponse));
            currentRequests.remove(client);
        }
    }

    /**
     * Looks for a response from the server to give the client.
     * Used by a server thread.
     * @param client : the client looking for a response
     * @return a formatted response object
     */
    public Response getResponse(int client) throws NoChangeException {
        Response response = currentResponses.get(client);
        if (response == null) {
            throw new NoChangeException();
        }
        currentResponses.remove(client);
        return response;
    }

//    /**
//     * Takes in a request, executes it as a command in the world, then returns a response
//     * @param request : The request the client sent
//     * @return the response to the request
//     */
//    public Response executeRequest(Request request){
//        this.requestLog.add(request);
//        if (!Objects.equals(request.getCommand(), "idle")) {
//            System.out.println(request.serialize());
//        }
//        Command command;
//        String commandResponse;
//        try {
//            command = Command.create(request);
//            commandResponse = command.execute(world);
//        } catch (IllegalArgumentException badCommand) {
//            commandResponse = "failed! bad input";
//        }
//        return generateResponse(request, commandResponse);
//    }


    public Response generateResponse(Request request, String commandResponse) {
        Robot robot = world.getRobot(request.getRobotName());
        HashMap<Integer, HashMap<Integer, String>> map = world.look(robot);
        HashMap<String, Robot> enemies = world.getEnemies(robot);

        Response response = new Response(
                robot,
                commandResponse,
                map,
                enemies
        );

        //TODO : use the look command robot position to get the grid of values it

        this.responseLog.add(response);
        if (!Objects.equals(response.getCommandResponse(), "idle")) {
            System.out.println(response.serialize()); // PRINT RESPONSE
        }
        return response;
    }


    public void dump(){

        //TODO : Display a representation of the world's state showing robots, obstacles, and anything else in the world.
        List<Obstacle> obstacleList=getMap().getObstacles();
        HashMap<String, Robot> robots = world.getRobots();
        System.out.println("The are some obstacles");
        for (Obstacle obstacle:obstacleList) {
            System.out.println("At position "+obstacle.getPosition().getX()+","+obstacle.getPosition().getY());
        }
        if (robots.values().size()>0) {
            System.out.println("R O B O T S:");
            for (Robot robot : robots.values()) {
                System.out.println(robot.toString());
            }
        } else {
            System.out.println("Robot not found");
        }
    }

    public void robots(){
        //TODO : List all robots in the world including the name and state, and output it
        HashMap<String, Robot> robots = world.getRobots();
        if (robots.values().size()>0) {
            System.out.println("ROBOTS:");
            for (Robot robot : robots.values()) {
                System.out.println(robot.toString());
            }
        } else {
            System.out.println("Robot not found");
        }
    }

    public void quit() {
      System.exit(0);
        //TODO : Should send a response to all clients telling them that the server is shutting down, then close everything that needs closing on the server side
    }


    /**
     * The server is run from here.
     * @param args : none are applicable
     * @throws IOException : raised when server object fails
     */
    public static void main(String[] args) throws IOException {
        InetAddress ip;
        String hostname;

        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        start();
    }
}

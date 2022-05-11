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

import static java.lang.Math.round;

/**
 * The server that will be run. Clients will connect to it. 
 * It has 2-way communication and support for many clients
 */
public class Server implements Runnable {
    /**
     * The speed at which the server sends out responses (in milliseconds)
     */
    private final int tickInterval = 50;
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
    private final HashMap<String, Request> currentRequests;
    /**
     * The list of responses that will be sent in the next server tick
     */
    private final HashMap<String, Response> currentResponses;

    /**
     * Stores history of requests made to the server
     */
    private List<Request> requestLog;
    /**
     * Stores history of responses made to the server
     */
    private List<Response> responseLog;


    /**
     * Makes an instance of a server. It uses the port given for the server socket
     * @param port : the port clients will use to connect to the server
     * @throws IOException : throws when server socket fails
     */
    private Server(int port) throws IOException{
        this.requestLog = new ArrayList<>();
        this.responseLog = new ArrayList<>();
        this.socket = new ServerSocket(port);
        Map map = getMap();
        this.world = new World(map);
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
        return new BasicMap(new Position(100,100));
    }

    /**
     * Starts up the server
     * @throws IOException : raised when server object fails
     */
    public static void start() throws IOException {
        final int port = 5000;
        Server server = new Server(port);

        Thread executeThread = new Thread(server);
        executeThread.start();

        Thread inputThread = new InputThread(server);
        inputThread.start();

        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                Socket socket = server.socket.accept();
                String socketName = socket.getLocalAddress().toString();
                System.out.println(socketName);

                ServerThread serverThread = new ServerThread(server, socket);
                serverThread.start();
                System.out.println("A client has been connected. Their name is : " + socket.getInetAddress().getHostName());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Executes all requests every tick
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

    /**
     * Allows a server thread to give the server a request for a client
     * @param robotName : the client giving the request
     * @param request : the request the client is giving
     */
    public void addRequest(String robotName, Request request){
        currentRequests.putIfAbsent(robotName, request);
    }

    /**
     * Should execute all requests and create a new response for each client
     * It should clear the list as it goes
     */
    private void executeRequests() {
        if (currentRequests.keySet().size() == 0) {
            return;
        }

        String commandResponse = "";
        for (String robotName : new HashSet<String>(){
                {
                    addAll(world.getRobots().keySet());
                    addAll(currentRequests.keySet());
                }
            }
        ) {
            robotName = robotName.trim().toLowerCase();

            Request request = currentRequests.getOrDefault(robotName, new Request(robotName, "idle"));

            try {
                this.requestLog.add(request);
                System.out.println(request.getCommand());

                if (!request.getCommand().equals("idle")) {
                    System.out.println(request.serialize()); // PRINT REQUEST
                }

                if (request.getCommand().equals("launch")) {
                    Command command = Command.create(request);
                    commandResponse = command.execute(world);
                } else {
                    if (!world.getRobot(robotName).isPaused()) {
                        Command command = Command.create(request);
                        commandResponse = command.execute(world);
                    }
                }
                world.getRobot(robotName).setLastcommand(request.getCommand());
            } catch (IllegalArgumentException e) {
                commandResponse = "failed! bad input";
            }

            //TODO : Add all requests to a buffer before adding them to current requests (to prevent multiple responses per request)

            //TODO : use the look command on each robot to get the grid of values it
            generateResponse(request, commandResponse);
            currentRequests.remove(robotName);
        }
    }

    /**
     * Takes a request and generates a response object
     * @param request : a request object received from the client
     * @param commandResponse : the response to the client's request
     * @return response
     */
    public void generateResponse(Request request, String commandResponse) {
        Robot robot = world.getRobot(request.getRobotName());
        HashMap<Integer, HashMap<Integer, String>> map = world.look(robot);
        HashMap<String, Robot> enemies = world.getEnemies(robot);

        Response response = new Response(
                robot,
                commandResponse,
                map,
                enemies
        );

        this.responseLog.add(response);

        currentResponses.put(robot.getRobotName().toLowerCase(), response);

        if (!Objects.equals(response.getCommandResponse(), "idle")) {
            System.out.println("Out -> " +response.getRobot().getRobotName() + " : " + response.getCommandResponse());
        }
    }

    /**
     * Looks for a response from the server to give the client.
     * Used by a server thread.
     * @param robotName : the client looking for a response
     * @return a formatted response object
     */
    public Response getResponse(String robotName) throws NoChangeException {
        Response response = currentResponses.get(robotName.toLowerCase());
        if (response == null) {
            throw new NoChangeException();
        }
        currentResponses.remove(robotName);
        return response;
    }

    /**
     * Display a representation of the world's state showing robots, obstacles, and anything else in the world.
     */
    public void dump(){
        List<Obstacle> obstacleList = getMap().getObstacles();
        HashMap<String, Robot> robots = world.getRobots();
        if (obstacleList.size()>0) {
            System.out.println("----------------------\n" +
                    "OBSTACLES:\n" +
                    "----------------------");
            for (Obstacle obstacle:obstacleList) {
                System.out.println(obstacle.toString());
            }
            System.out.println("----------------------");
        }
        if (robots.values().size()>0) {
            System.out.println("ROBOTS:");
            for (Robot robot : robots.values()) {
                System.out.println("----------------------\n" +
                        robot.toString());
            }
            System.out.println("----------------------");
        }
        System.out.println("----------------------\n" +
                "WORLD MAP:\n" +
                "----------------------");
        HashMap<Integer, HashMap<Integer, String>> map = world.look(new Position(0,0), world.getMapSize().getX()/2+2);
        for (int y = map.get(0).size()-1; y >= 0;  y--){
            for (int x =0; x < map.size(); x++){
                try{
                    System.out.print("" + map.get(x).get(y).charAt(0) /*+ map.get(x).get(y).charAt(0)*/);
                } catch (NullPointerException odd) {
                    System.out.println(x + "," + y);
                }
            }
            System.out.print('\n');
        }
    }

    /**
     * Display a summary of all robots and their states
     */
    public void robots(){
        HashMap<String, Robot> robots = world.getRobots();
        if (robots.values().size()>0) {
            System.out.println("----------------------\n" +
                    "ROBOTS:");
            for (Robot robot : robots.values()) {
                System.out.println("----------------------\n" +
                        robot.toString());
            }
            System.out.println("----------------------");
        } else {
            System.out.println("No robots in world");
        }
    }

    /**
     * Should send a response to all clients telling them that the server is shutting down,
     * then close everything that needs closing on the server side
     */
    public void quit() {
        for (Robot robot : world.getRobots().values()) {
            Response response = new Response(robot, "quit", world.look(robot), world.getEnemies(robot));
            currentResponses.put(robot.getRobotName(), response);
            try {
                Thread.sleep(tickInterval*2);
            } catch (InterruptedException ignored) {
            }
        }
        System.exit(0);
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

    public static class InputThread extends Thread{

        private final Server server;

        public InputThread(Server server) {
            this.server = server;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().toLowerCase();
                switch (command) {
                    case "quit":
                        server.quit();
                        break;
                    case "dump":
                        server.dump();
                        break;
                    case "robots":
                        server.robots();
                        break;
                    default:
                        System.out.println("Unsupported command: " + command);
                }
            }
        }
    }
}

package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.server.command.Command;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoChangeException;
import za.co.wethinkcode.robotworlds.shared.exceptions.RobotNotFoundException;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;
import za.co.wethinkcode.robotworlds.shared.protocols.Response;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Server{
    /**
     * The server port number
     */
    private static final int port = Integer.parseInt(getConfigProperty("port"));
    /**
     * The speed at which the server sends out responses (in milliseconds)
     */
    private static final int tickRate = Integer.parseInt(getConfigProperty("tickRate"));
    /**
     * The socket clients will connect to
     */
    private static final ServerSocket socket = getServerSocket();
    /**
     * The list of requests that will be run in the next server tick
     */
    private static final HashMap<String, Request> currentRequests = new HashMap<>();
    /**
     * The list of responses that will be sent in the next server tick
     */
    private static final HashMap<String, Response> currentResponses = new HashMap<>();

    /**
     * Stores history of requests made to the server
     */
    private static final List<Request> requestLog = new ArrayList<>();
    /**
     * Stores history of responses made to the server
     */
    private static final List<Response> responseLog= new ArrayList<>();

    /**
     * Gets a generic property from the server config file
     * @param property the property key
     * @return the property value
     */
    public static String getConfigProperty(String property){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String value = properties.getProperty(property);
            if (value != null){
                return value;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        System.out.println("Property " + property + " is not defined in the config");
        System.exit(1);
        return "";
    }

    /**
     * Safely creates server socket
     * @return the instantiated server socket object
     */
    private static ServerSocket getServerSocket(){
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows the address and port for the clients to connect to
     */
    private static void showIP(){
        InetAddress ip;
        String hostname;

        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostAddress();
            System.out.println("Server IP : " + hostname + ":" + port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to get a client that's trying to connect to the server.
     */
    private static void getClient(){
        try {
            Socket socket = Server.socket.accept();

            ClientSocket.startClientThreads(socket);

            System.out.println("A client ("
                    + socket.getInetAddress().getHostName()
                    + ") has been connected. Waiting for launch...");

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Starts up the server
     */
    public static void start(){
        showIP();

        Thread executeThread = new RequestExecuteThread();
        executeThread.start();

        Thread inputThread = new InputThread();
        inputThread.start();

        System.out.println("Server running & waiting for client connections.");
        while(true) {
            getClient();
        }
    }

    /**
     * Allows a server thread to give the server a request for a client
     * @param robotName : the client giving the request
     * @param request : the request the client is giving
     */
    public static void addRequest(String robotName, Request request){
        currentRequests.putIfAbsent(robotName, request);
    }

    /**
     * Should execute all requests and create a new response for each client
     * It should clear the list as it goes
     */
    private static void executeRequests() {
        long start = System.currentTimeMillis();

        if (currentRequests.keySet().size() == 0) {
            return;
        }

        String commandResponse = "";
        for (String robotName : new HashSet<String>(){
                {
                    addAll(World.getRobots().keySet());
                    addAll(currentRequests.keySet());
                }
            }
        ) {

            Request request = currentRequests.getOrDefault(robotName, new Request(robotName, "idle"));

            try {
                requestLog.add(request);


                if (!request.getCommand().equals("idle")) {
                    System.out.println(request.getRobotName()
                            + " -> " + request.getCommand()
                            + (request.getArguments() != null ? " - " + request.getArguments() : ""));
                }

                if (request.getCommand().equalsIgnoreCase("launch")) {
                    Command command = Command.create(request);
                    commandResponse = command.execute();
                } else try {
                    Robot robot = World.getRobot(robotName);

                    if (robot.hasDied()) {
                        robot.setPaused(true);
                        generateResponse(request, "You are dead");
                        currentRequests.remove(robotName);
                        World.remove(robotName);
                        continue;
                    }

                    if (!robot.isPaused() || request.getCommand().equalsIgnoreCase("quit")) {
                        robot.setLastCommand(request.getCommand());
                        Command command = Command.create(request);
                        commandResponse = command.execute();
                    }
                } catch (RobotNotFoundException robotDead){
                    continue;
                }


                if (request.getCommand().equalsIgnoreCase("quit")){
                    System.out.println(request.getRobotName() + " has quit");
                    currentRequests.remove(robotName);
                    continue;
                }

                World.getRobot(robotName).setLastCommand(request.getCommand());
            } catch (IllegalArgumentException e) {
                commandResponse = "failed! bad input";
            }

            generateResponse(request, commandResponse);
            currentRequests.remove(robotName);

            long end = System.currentTimeMillis();
            if (end-start > tickRate){
                System.out.println("Tick delay in milli seconds: "+ (end-start-tickRate));
            }
        }
    }

    /**
     * Takes a request and generates a response object
     * @param request : a request object received from the client
     * @param commandResponse : the response to the client's request
     */
    public static void generateResponse(Request request, String commandResponse) {
        Robot robot = World.getRobot(request.getRobotName());
        HashMap<Integer, HashMap<Integer, String>> map = World.look(robot);
        HashMap<String, Robot> enemies = World.getEnemies(robot);


        if (robot.hasDied()) {
            World.remove(robot);
            commandResponse = "You are dead";
        }

        Response response = new Response(
                robot,
                commandResponse,
                map,
                enemies
        );

        responseLog.add(response);

        currentResponses.put(robot.getRobotName(), response);

        if (!Objects.equals(response.getCommandResponse(), "idle")) {
            System.out.println(response.getRobot().getRobotName() + " -> " + response.getCommandResponse());
        }
    }

    /**
     * Looks for a response from the server to give the client.
     * Used by a server thread.
     * @param robotName : the client looking for a response
     * @return a formatted response object
     */
    public static Response getResponse(String robotName) throws NoChangeException {
        Response response = currentResponses.get(robotName);
        if (response == null) {
            throw new NoChangeException();
        }
        currentResponses.remove(robotName);
        return response;
    }

    /**
     * Display a representation of the world's state showing robots, obstacles, and anything else in the world.
     */
    public static void dump(){
        if (requestLog.size()>0) {
            System.out.println("----------------------\n" +
                    "REQUESTS:\n" +
                    "----------------------");
            for (Request request : requestLog) {
                System.out.println(request.getRobotName()
                        + " -> " + request.getCommand()
                        + (request.getArguments() != null ? " - " + request.getArguments() : ""));
            }
        }
        if (responseLog.size()>0) {
            System.out.println("----------------------\n" +
                    "RESPONSES:\n" +
                    "----------------------");
            for (Response response : responseLog) {
                System.out.println(response.getRobot().getRobotName() + " -> " + response.getCommandResponse());
            }
        }

        if (World.getRobots().values().size()>0) {
            System.out.println("ROBOTS:");
            for (Robot robot : World.getRobots().values()) {
                System.out.println("----------------------\n" +
                        robot.toString());
            }
            System.out.println("----------------------");
        }
        System.out.println("----------------------\n" +
                "WORLD MAP:\n" +
                "----------------------");
        HashMap<Integer, HashMap<Integer, String>> map = World.look(new Position(0,0),
                (World.getMapSize().getX() > World.getMapSize().getY()) ?
                        (World.getMapSize().getX()/2+2): World.getMapSize().getY()/2+2);
        for (int y = map.get(0).size()-1; y >= 0;  y--){
            for (int x =0; x < map.size(); x++){
                try{
                    System.out.print("" + map.get(x).get(y).charAt(0));
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
    public static void robots(){
        HashMap<String, Robot> robots = World.getRobots();
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
    public static void quit() {
        for (Robot robot : World.getRobots().values()) {
            Response response = new Response(robot, "quit", null, null);
            currentResponses.put(robot.getRobotName(), response);
            try {
                Thread.sleep(tickRate);
            } catch (InterruptedException ignored) {
            }
        }
        System.exit(0);
    }

    /**
     * The server is run from here.
     * @param args : none are applicable
     */
    public static void main(String[] args){
        start();
    }

    /**
     * Executes all requests every tick
     */
    private static class RequestExecuteThread extends Thread {
        @Override
        public void run() {
            do {
                executeRequests();
                try {
                    Thread.sleep(tickRate);
                } catch (InterruptedException ignored) {
                }
            } while (true);
        }
    }

    /**
     * A separate thread to be run to handle server commands
     */
    public static class InputThread extends Thread{
        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().toLowerCase();
                switch (command) {
                    case "quit":
                        quit();
                        break;
                    case "dump":
                        dump();
                        break;
                    case "robots":
                        robots();
                        break;
                    default:
                        System.out.println("Unsupported command: " + command);
                }
            }
        }
    }
}

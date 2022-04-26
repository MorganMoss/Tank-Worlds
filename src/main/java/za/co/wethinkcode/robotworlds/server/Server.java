package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.command.Command;
import za.co.wethinkcode.robotworlds.server.command.IdleCommand;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

/**
 * The server that will be run. Clients will connect to it. 
 * It has 2-way communication and support for many clients
 */
public class Server{
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
        world = new World(getMap());
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
     * Takes in a request, executes it as a command in the world, then returns a response
     * @param request : The request the client sent
     * @return the response to the request
     */
    public Response executeRequest(Request request){
        this.requestLog.add(request);
        //TODO : Add all requests and responses to a log, that can dumped to a file later
        System.out.println(request.serialize());

        Command command;
        String commandResponse;
        try {
            command = Command.create(request);
            commandResponse = command.execute(world);
        } catch (IllegalArgumentException badCommand) {
            commandResponse = "failed! bad input";
        }
        return generateResponse(request, commandResponse);
    }


    public Response generateResponse(Request request, String commandResponse) {
        HashMap<Integer, HashMap<Integer, String>> map = world.look(new Position(0,0));
        Robot robot = world.getRobot(request.getRobotName());
        HashMap<String, Robot> enemies = world.getEnemies(map);
        enemies.remove(robot.getRobotName());

        Response response = new Response(
                robot,
                commandResponse,
                map,
                enemies
        );

        //TODO : use the look command robot position to get the grid of values it

        this.responseLog.add(response);
        System.out.println(response.serialize());

        return response;
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

    public void dump(){
        //TODO : Set up the dump function. It should output a list of the robots, with their current states and the world and all it's info
    }

    public void robots(){
        //TODO : Get a list of robots with their current info, and output it.
    }

    public void quit(){
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

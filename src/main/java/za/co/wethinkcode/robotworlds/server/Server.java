package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.command.Command;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
//    /**
//     * Used to indicate the number of clients connected to the server,
//     * as well as to give a new client a number.
//     */
//    public int clientCount = 0;


    /**
     * Makes an instance of a server. It uses the port given for the server socket
     * @param port : the port clients will use to connect to the server
     * @throws IOException : throws when server socket fails
     */
    private Server(int port) throws IOException{
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
        return new BasicMap(new Position(200,200));
    }

    public static ArrayList<String> clientNames = new ArrayList<>();
    public void addClient(String clientName){
        clientNames.add(clientName);
    }

    /**
     * Takes in a request, executes it as a command in the world, then returns a response
     * @param request : The request the client sent
     * @return the response to the request
     */
    public Response executeRequest(Request request){
        //TODO : Add all requests and responses to a log, that can dumped to a file later
        System.out.println(request.serialize());

        try {
            //TODO : Implement Commands, they should be executed by a specific robot
            Command command = Command.create(request);
            command.execute(world, request.getClientName());
        } catch (IllegalArgumentException badCommand) {
            //TODO : Error Handling
        }

        //TODO : use the look command robot position to get the grid of values it
        Response response = new Response(request.getClientName(), world.getRobot(request.getClientName()).serialize(), world.look(new Position(0,0)));

        System.out.println(response.serialize());

        return response;
    }

    /**
     * Starts up the server
     * @throws IOException : raised when server object fails
     */
    public static void start() throws IOException {
        final int port = 5001;
        Server server = new Server(port);

        System.out.println("Server running & waiting for client connections.");

        while(true) {
            //TODO : Setup a separate input thread, so that commands like 'quit', 'dump' and 'robots' can be handled in the main loop
            try {
                Socket socket = server.socket.accept();

                String socketName = socket.getLocalAddress().toString();
                System.out.println(socketName);
                System.out.println(clientNames);

                if (!clientNames.contains(socketName)){
                    clientNames.add(socketName);
                    server.clientCount += 1;
                }
                ServerThread serverThread = new ServerThread(server, socket, server.clientCount);
                serverThread.start();
                System.out.println("A client has been connected. Their name is : " + socket.getLocalAddress());
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

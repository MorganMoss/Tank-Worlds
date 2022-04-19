package za.co.wethinkcode.robotworlds.client;

import java.net.InetAddress;
import java.net.Socket;

import za.co.wethinkcode.robotworlds.client.SwingUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingUI.TankWorld;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import javax.swing.*;
import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static int port = 5000;
    private static String clientName;
    public static String getMyClientName(){return clientName;}
    static Scanner scanner = new Scanner(System.in);
    private static String enemyName;
    public String getEnemyName(){return this.enemyName;};

    /**
     * Starts the gui and the threads that handle input/output
     * @param args : discarded
     */
    public static void main(String args[]) {
        
//        GUI gui = new TextGUI();

        //print out ip address and server
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

        // Get player name
        System.out.println("Enter Tank Name: ");
        clientName = scanner.nextLine();

        //Start swing gui
        HelperMethods.setTheme();
        JFrame frame = new JFrame("T A N K W O R L D S");
        frame.setIconImage(HelperMethods.getImage("/icon.png"));
        frame.setSize(TankWorld.getScreenWidth(), TankWorld.getScreenHeight());
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TankWorld gui = new TankWorld();

        frame.add(gui);
        gui.setFocusable(true);
        frame.setVisible(true);
        gui.start();


        //start socket
        try (
                //10.200.109.17
            Socket socket = new Socket("localhost", port);
        ) {
            Out output = new Out(socket, gui);
            output.start();

            In input = new In(socket, gui);
            input.start();
            
            while (input.isAlive() && output.isAlive()) {}
            
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } 

    }
}

/**
 * Receives responses from server and makes GUI output them
 */
class Out extends Thread {
    private final GUI gui;
    private final Socket socket;

    public Out(Socket socket, GUI gui){
        this.socket = socket;
        this.gui = gui;
    }
    
    @Override
    public void run() {
        try(
            BufferedReader incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            do {
                try {
                    String serializedResponse = incoming.readLine(); //should be changed to a response object
                    if (!serializedResponse.matches("")){
//                        gui.showOutput(Response.deSerialize(serializedResponse));
                        System.out.println(serializedResponse);
                        System.out.println(gui.getClientName());
                        if (!serializedResponse.contains(gui.getClientName())){
                            Response deserializedResponse = Response.deSerialize(serializedResponse);
                            String enemyName = deserializedResponse.getClientName();
                            gui.setEnemyName(enemyName);

                            if(serializedResponse.contains("forward")){
                            gui.enemyMovement("forward");
                            }else if(serializedResponse.contains("back")){
                                gui.enemyMovement("forward");
                            }else if(serializedResponse.contains("left")){
                                gui.enemyMovement("left");
                            }else if(serializedResponse.contains("right")){
                                gui.enemyMovement("right");
                            }
                        }
                    }
                } catch (IOException ignored) {}
                
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/**
 * Gives requests to server, the input for which is given by the GUI
 */
class In extends Thread {
    private String input;
    private final GUI gui;
    private final Socket socket;
    
    public In(Socket socket, GUI gui) {
        this.gui = gui;
        this.socket = socket;
    }

    @Override
    public void run() {
        socket.setPerformancePreferences(1,0,2);
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try(
            PrintStream outgoing = new PrintStream(socket.getOutputStream());
        ) {
            do {
                try {
                    input = gui.getInput();
                    System.out.println("input:\n"+input);
                    Request request = new Request(gui.getClientName(), input);
                    String serializedRequest = request.serialize();

                    outgoing.println(serializedRequest); 
                    outgoing.flush();
    
                    if (input.matches("quit")) {
                        System.exit(0);
                    }
                } catch (NoNewInput ignored) {}

            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

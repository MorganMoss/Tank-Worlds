package za.co.wethinkcode.robotworlds.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.SwingGUI;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.robot.Robot;


import javax.swing.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the back-end for the user, it handles communication between the GUI and the server.
 * This class should not be modified further, outside of the TODO's given.
 */
public class Client {
    //TODO : Have port be determined by a config file
    /**
     * The port the server uses
     */
    private static int port = 5000;
    private static String clientName;
    static Scanner scanner = new Scanner(System.in);
    private static String enemyName="enemy";
    private static Response guiResponse;

    public static String getMyClientName(){return clientName;}

    public static Response getResponse() {
        return guiResponse;
    }

    public String getEnemyName(){return enemyName;};

    /**
     * Starts the gui and the threads that handle input/output
     */
    private static void start(){
        //TODO : Have a config file for client that sets which GUI you use
        /*
          all that should change in here is this line.
          i.e. GUI gui = new SwingGUI(); or new TankWorlds();
         */
//        GUI gui = new SwingGUI();
//        ((SwingGUI) gui).startGUI();
//        GUI gui = new TextGUI();
        System.out.println("Enter Tank name: \n");
        clientName = scanner.nextLine();

        HelperMethods.setTheme();
        JFrame frame = new JFrame("T A N K W O R L D S");
        frame.setIconImage(HelperMethods.getImage("icon.png"));
//        JLabel background = new JLabel(new ImageIcon(HelperMethods.getImage("/icon.png")));
        frame.setSize(TankWorld.getScreenWidth(), TankWorld.getScreenHeight());
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TankWorld gui = new TankWorld();
        frame.add(gui);
        gui.setFocusable(true);
        frame.setVisible(true);
        gui.start();

        try (
                //10.200.109.17 //localhost //maggie 10.200.110.163
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

    /**
     * Run client from here
     * @param args : does nothing
     */
    public static void main(String[] args) {
       Client.start();
    }

    /**
     * Receives responses from server and makes GUI output them
     */
    private static class Out extends Thread {
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
                        guiResponse = Response.deSerialize(serializedResponse);
//                        System.out.println(guiResponse.getEnemyRobots().values());
                        System.out.println("("+(guiResponse.getRobot().getPosition().getX()+300)+","+(-guiResponse.getRobot().getPosition().getY()+300)+")");

                        if (!serializedResponse.matches("")){
    //                        gui.showOutput(Response.deSerialize(serializedResponse));
                            System.out.println(serializedResponse);
                            System.out.println(gui.getClientName());
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
    private static class In extends Thread {
        private final GUI gui;
        private final Socket socket;
//        private final String name;

        public In(Socket socket, GUI gui) {
            this.gui = gui;
            this.socket = socket;
//            gui.showOutput(new Response("", "Enter a Name", new HashMap<>()));
//            this.name = gui.getInput();
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
                        String input = gui.getInput().serialize();
                        System.out.println("output:\n"+input);

                        Request request = new Request(gui.getClientName(), input);
                        String serializedRequest = request.serialize();

                        outgoing.println(input);
                        outgoing.flush();

                        if (request.getCommand().matches("quit")) {
                            System.exit(0);
                        }
                    } catch (NoNewInput ignored) {}

                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

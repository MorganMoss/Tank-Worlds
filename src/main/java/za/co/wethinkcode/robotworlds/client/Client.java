package za.co.wethinkcode.robotworlds.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

/**
 * This is the back-end for the user, it handles communication between the GUI and the server.
 * This class should not be modified further, outside of the TODO's given.
 */
public class Client {
    //TODO : Have port and address be determined by a config file
    /**
     * The port the server uses
     */
    private static int port = 5000;
    //10.200.109.17 //localhost //maggie 10.200.110.163
    /**
     * The address of the server that the client is connecting to
     */
    private static String address = "localhost";

    //TODO : Have a config file for client that sets which GUI you use
    /**
     * The GUI being used by the client
     */
    private static class CurrentGUI extends TextGUI {}

    /**
     * Starts the gui and the threads that handle input/output
     */
    private static void start(){
        GUI gui = new CurrentGUI();

        try (Socket socket = new Socket(address, port)) {
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
                        String serializedResponse = incoming.readLine();

                        Response response = Response.deSerialize(serializedResponse);

                        if (response != null) {
                            gui.showOutput(response);
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
                        Request request = gui.getInput();

//                        System.out.println("Out -> " + request.getRobotName() + " : " + request.getCommand() + " ; " + request.getArguments());

                        outgoing.println(request.serialize()); //send request to server
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

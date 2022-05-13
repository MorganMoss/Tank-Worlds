package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;
import za.co.wethinkcode.robotworlds.shared.protocols.Response;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;

/**
 * This is the back-end for the user, it handles communication between the GUI and the server.
 */
public class Client {
    /**
     * The port the server uses
     */
    private static final int port = Integer.parseInt(getConfigProperty("port"));

    /**
     * The address of the server that the client is connecting to
     */
    private static final String address = getConfigProperty("address");

    /**
     * The GUI being used by the client
     */
    private static class CurrentGUI extends TankWorld {}

    //these can be removed after debugging lag
    private static Long start;
    private static final int tickRate = 50;

    /**
     * Gets a generic property from the client config file
     * @param property the property key
     * @return the property value
     */
    public static String getConfigProperty(String property){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties.getProperty(property);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        System.exit(1);
        return "";
    }

    /**
     * Starts the gui and the threads that handle input/output
     */
    private static void start(){
        GUI gui = new CurrentGUI();

        try (Socket socket = new Socket(address, port)) {
            ResponseThread output = new ResponseThread(socket, gui);
            output.start();

            RequestThread input = new RequestThread(socket, gui);
            input.start();

            while (input.isAlive() && output.isAlive()) {}

            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
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
    private static class ResponseThread extends Thread {
        private final GUI gui;
        private final Socket socket;

        public ResponseThread(Socket socket, GUI gui){
            this.socket = socket;
            this.gui = gui;
        }

        @Override
        public void run() {
            try(
                BufferedReader incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                do {
                    try {
                        String serializedResponse = incoming.readLine();

                        Response response = Response.deSerialize(serializedResponse);

                        if (System.currentTimeMillis() - start > tickRate){
                            System.out.println("Response delayed by " + (System.currentTimeMillis() - start - tickRate));
                        }

                        if (response != null) {
                            gui.showOutput(response);
                        }

                    } catch (IOException ignored) {}

                } while (true);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Gives requests to server, the input for which is given by the GUI
     */
    private static class RequestThread extends Thread {
        private final GUI gui;
        private final Socket socket;

        public RequestThread(Socket socket, GUI gui) {
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
                PrintStream outgoing = new PrintStream(socket.getOutputStream())
            ) {
                do {
                    Client.start = System.currentTimeMillis();
                    try {
                        Request request = gui.getInput();

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

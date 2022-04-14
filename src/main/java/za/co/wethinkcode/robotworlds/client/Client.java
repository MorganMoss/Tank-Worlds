package za.co.wethinkcode.robotworlds.client;

import java.net.Socket;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import java.io.*;

public class Client {
    /**
     * The port the server uses
     */
    private static int port = 5000;

    /**
     * Starts the gui and the threads that handle input/output
     */
    private static void start(){
        GUI gui = new TextGUI();

        try (
                Socket socket = new Socket("LocalHost", port);
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
    public static void main(String args[]) {
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
                        if (!serializedResponse.matches("")){
                            gui.showOutput(Response.deSerialize(serializedResponse));
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
        private String input;
        private final GUI gui;
        private final Socket socket;

        public In(Socket socket, GUI gui) {
            this.gui = gui;
            this.socket = socket;
        }

        @Override
        public void run() {
            try(
                    PrintStream outgoing = new PrintStream(socket.getOutputStream());
            ) {
                do {
                    try {
                        input = gui.getInput();
                        Request request = new Request("Client", input);
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
}

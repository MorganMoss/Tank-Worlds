package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.exceptions.NoChangeException;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Objects;

/**
 * A thread that allows 2-way communication 
 * between a client and the server.
 */
public class ServerThread extends Thread {
    /**
     * The server object this thread belongs to
     */
    private final Server server;
    /**
     * The client's requests are received from this
     */
    private final BufferedReader in;
    /**
     * The responses are sent to the client from this
     */
    private final PrintStream out;

    /**
     * Makes a new thread for the server for a client that has just connected.
     * It will allow for communication between a client and the server when run
     *
     * @param server : the server this is run from
     * @param socket : the socket used to connect the client to the server
     * @throws IOException : the connection failed
     */
    public ServerThread(Server server, Socket socket) throws IOException {
        this.server = server;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintStream(socket.getOutputStream());

        System.out.println("Connection from " + socket.getInetAddress().getHostName());
    }

    /**
     * Waits for a request from client, gives it to the server
     * Then will wait for server to respond and sends that response to the client
     */
    @Override
    public void run() {
        Responder responder = new Responder(server, out);
        responder.start();

        Requester requester = new Requester(server, in, responder);
        requester.start();

        while (requester.isAlive() && responder.isAlive()) {
        }

        closeQuietly();
    }




    /**
     * Closes the communication without raising errors
     */
    private void closeQuietly() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }

    /**
     * The thread used to send responses from the server to the client
     */
    private static class Responder extends Thread {
        private final Server server;
        private final PrintStream out;
        private String robotName = "";

        public Responder(Server server, PrintStream out) {
            this.server = server;
            this.out = out;
        }

        /**
         * Get all responses from currentResponses, serialize it and send to out
         * */
        @Override
        public void run() {
            while (true) {
                try {
                    Response response = server.getResponse(robotName);
                    out.println(response.serialize());
                } catch (NoChangeException ignored) {
                }
            }
        }

        public void setRobotName(String robotName) {
            this.robotName = robotName;
        }
    }

    /**
     * The thread used to get requests from the client and give them to the server
     */
    private static class Requester extends Thread {
        private final Server server;
        private final BufferedReader in;
        private final Responder responder;

        public Requester(Server server, BufferedReader in, Responder responder) {
            this.server = server;
            this.in = in;
            this.responder = responder;
        }

        /**
         * Get String request from in, deserialize request and add to currentRequests
         * */
        @Override
        public void run() {
            String messageFromClient;
            String robotName = "";
            try {
                while ((messageFromClient = in.readLine()) != null) {
                    Request request = Request.deSerialize(messageFromClient);
                    if (Objects.equals(request.getCommand(), "launch")) {
                        responder.setRobotName(request.getRobotName());
                    }
                    server.addRequest(robotName, request);
                }
            } catch (IOException ex) {
                System.out.println("Shutting down client");
            }
        }
    }
}
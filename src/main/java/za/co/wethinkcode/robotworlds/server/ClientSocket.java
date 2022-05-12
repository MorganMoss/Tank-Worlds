package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoChangeException;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;
import za.co.wethinkcode.robotworlds.shared.protocols.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Threads that allows 2-way communication
 * between a client and the server are run.
 */
public class ClientSocket{
    /**
     * Starts the Request and Response Thread, they automatically close when necessary
     * It will allow for communication between a client and the server
     * @param socket : the socket used to connect the client to the server
     * @throws IOException : socket is broken
     */
    public static void startClientThreads(Socket socket) throws IOException {
        RequestThread requestThread = new RequestThread(socket);
        requestThread.start();
    }

    /**
     * The thread used to send responses from the server to the client
     */
    private static class ResponseThread extends Thread {
        private final PrintStream out;
        private String robotName = "";

        private boolean hasLaunched = false;

        public ResponseThread(PrintStream out) {
            this.out = out;
        }

        /**
         * Get all responses from currentResponses, serialize it and send to out
         * */
        @Override
        public void run() {
            while (true) {
                try {
                    Response response = Server.getResponse(robotName);
                    out.println(response.serialize());
                    if (response.getRobot().getLastCommand().equalsIgnoreCase("Launch")
                            && !response.getCommandResponse().equalsIgnoreCase("Success")
                    ){
                        hasLaunched = false;
                        break;
                    } else {
                        hasLaunched = true;
                    }
                } catch (NoChangeException ignored) {
                }
            }
        }

        public void setRobotName(String robotName) {
            this.robotName = robotName;
        }

        public boolean isLaunched() {
            return hasLaunched;
        }
    }

    /**
     * The thread used to get requests from the client and give them to the server
     */
    private static class RequestThread extends Thread {
        private final BufferedReader in;
        private final ResponseThread responseThread;

        public RequestThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            responseThread = new ResponseThread(new PrintStream(socket.getOutputStream()));
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

                    if (request.getCommand().equals("launch")) {
                        if (!responseThread.isLaunched()) {
                            responseThread.start();
                            responseThread.setRobotName(request.getRobotName());
                            robotName = request.getRobotName();
                            System.out.println(robotName + " has joined!");
                        } else continue;
                    }

                    Server.addRequest(robotName, request);
                }
            } catch (IOException ex) {
                System.out.println(robotName + " is shutting down");
                Server.addRequest(robotName, new Request(robotName, "quit"));
            }

            try {
                responseThread.out.close();
                in.close();
            } catch (IOException ignored) {
            }
        }
    }
}
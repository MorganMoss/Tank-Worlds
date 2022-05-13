package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.NoChangeException;
import za.co.wethinkcode.robotworlds.shared.exceptions.RobotNotFoundException;
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

                    if (response.getCommandResponse().equalsIgnoreCase("quit successful")){
                        break;
                    }

                    if (response.getRobot().getLastCommand().equalsIgnoreCase("Launch")
                            && !response.getCommandResponse().equalsIgnoreCase("Success")
                    ){
                        hasLaunched = false;
                        robotName = "";
                        break;
                    } else {
                        hasLaunched = true;
                    }

                    if (response.getCommandResponse().equalsIgnoreCase("You are dead")){
                        hasLaunched = false;
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

        public void sendResponse(Response response) {
            out.println(response.serialize());
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
            if (!responseThread.isAlive()){
                responseThread.start();
            }
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

                    if (request.getCommand().equalsIgnoreCase("quit")){
                        break;
                    }

                    if (!responseThread.isLaunched()) {
                        if (request.getCommand().equals("launch")) {
                            try {
                                World.getRobot(request.getRobotName());
                                responseThread.sendResponse(new Response(null, "Robot with that name already exists.",null,null));
                                continue;
                            } catch (RobotNotFoundException goAsNormal) {
                                System.out.println(robotName + " has joined!");
                                responseThread.setRobotName(request.getRobotName());
                                robotName = request.getRobotName();
                            }
                        }
                    }

                    Server.addRequest(robotName, request);
                }

                if (responseThread.isLaunched()){
                    System.out.println(robotName + " is shutting down");
                    Server.addRequest(robotName, new Request(robotName, "quit"));
                } else {
                    System.out.println("Client shut down before launching");
                }

                try {
                    responseThread.out.close();
                    in.close();
                } catch (IOException ignored) {
                }

            } catch (IOException ignored) {}
        }
    }
}
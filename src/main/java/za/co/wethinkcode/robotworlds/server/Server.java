package za.co.wethinkcode.robotworlds.server;

import java.net.ServerSocket;

public class Server {
    public final World world;
    public final ServerSocket socket;
    public List<Request> currentRequests;
    public List<Response> currentResponses;

    public static void main(String[] args) {}

    public void setup() {}

    public void executeRequests() {}

    public void sendResponses() {}
}

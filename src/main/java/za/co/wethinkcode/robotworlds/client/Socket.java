package za.co.wethinkcode.robotworlds.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

// Client class
class Socket {
    static String name;
    public void setName(String name){
        Socket.name =name;};

    // driver code
    public static void run()
    {

        // establish a connection by providing host and port
        // number
        try (java.net.Socket socket = new java.net.Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            // reading from user
            String line = TankWar.getRequest();

            if (!"exit".equalsIgnoreCase(line)) {

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server: Socket "+name+" command: "
                        + in.readLine());
            }

            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

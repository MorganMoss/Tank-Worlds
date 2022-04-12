package za.co.wethinkcode.robotworlds.client;

import java.net.Socket;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import java.io.*;

public class Client {
    public final String robotName;
    public final Socket socket;
    public Request currentRequest;
    public Response currentResponse;

    public static void main(String args[]) {
        // start up gui
        GUI gui = new TextGUI();
        Thread guiThread = new Thread(gui);
        guiThread.start();

        final int port = 5000;

        try (
            Socket socket = new Socket("LocalHost", port);
            PrintStream out = new PrintStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        )
        {
            class Out extends Thread {
                private final BufferedReader in;
                private final GUI gui;

                public Out(BufferedReader in,GUI gui){
                    this.in = in;
                    this.gui = gui;
                }
                
                @Override
                public void run() {
                    while(true) try {
                        if (in.ready()) {
                        String response = in.readLine(); //should be changed to a response object
                        gui.showOutput(new Response(response));
                        }
                    } catch (IOException ignored) {}
                }
            }

            Out output = new Out(in, gui);
            output.start();

            
            String input;

            do {
                input = "";

                try {
                    input = gui.getInput(); //tries to get input constantly
                    Request request = new Request(input); //request will take something like "forward 5" from input

                    // TODO need to serealize the request object before sending it to server
                    String serializedRequest = "TODO : " + request;

                    out.println(input); 
                    out.flush();
                } catch (NoNewInput ignored) {}


                if (input.matches("quit")) {
                    System.exit(0);
                }

            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        } 

    }

    public Client(String robotName, Socket socket) {
        this.robotName = robotName;
        this.socket = socket;
    }

    void giveRequest(String input) {}

    void getResponse() {}
}

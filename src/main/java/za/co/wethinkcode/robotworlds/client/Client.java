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

//        Since Java 7 we can use a try-with-resource structure (note the '(' after try instead of an
//        opening '{' ). This allows us to define resources that are closeable, and use those in the
//        try clause, knowing that the JVM will close it for us when we are done.
        try (
//                The Socket class in Java allows us to establish a TCP/IP connection to a server
//                application. We need to provide the IP address of the server (or localhost if it
//                runs on the same machine), as well as a port that it connects to.
                Socket socket = new Socket("localhost", 5000);

//                	We send data to the server by using the socket.getOutputStream(), which we can
//                	use as a normal OutputStream, just like we wrote files or to standard out.
                PrintStream out = new PrintStream(socket.getOutputStream());

//                	The socket.getInputStream() is used to receive messages from the server.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        )
        {
//            send message to server
            out.println("Hi this is Maggie");

//            	An output stream can buffer data until some minimum amount of data is buffered,
//            	before sending it. By calling out.flush() we ensure whatever is in the buffer gets
//            	sent immediately.
            out.flush();

//            Read the message returned from the server as a string value and print:
            String messageFromServer = in.readLine();
            System.out.println("Response: "+messageFromServer);

//            When we make use of any InputStream or OutputStream code we can get IOExceptions when
//            something goes wrong. We need to catch those and handle it. By using the
//            'try-with-resource' structure, we know that even if exceptions occur,
//            the socket connection will be closed safely.
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

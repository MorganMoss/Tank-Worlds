package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

/**
 * Interface for GUI's to have when run by the client
 */
public interface GUI{
    //TODO : Possibly a private quit() for game over
    // (i.e. their robot in their most recent response has a currentShield of 0)
    // or client quitting manually.
    // this can be done from showOutput, then quit can be a private method called by showOutput,
    // so that is not called outside the implementation.

    // TODO : Reducing the method calling that the client has to do to get the GUI to do what it needs
    //  is good, since the Client should only be handling the communication.
    //  GUI is to handle all the output and input. It is the front-end. The client is back-end

    //TODO : Discuss return for getInput. Perhaps it should rather return a Request than just a string.
    // Especially relevant if the GUI holds the robot name. It's a small change,
    // but may simplify code and reduce sharing of variables
    // I like this idea so I've done a quick reimplementation. Use TextGUI as reference.

    /**
     * The client will try to get input from the GUI constantly to make a request to the server
     * @return The user's latest input
     * @throws NoNewInput : if the user has not given input since last asked
     */
    public Request getInput() throws NoNewInput;

    /**
     * Takes info from the response to draw the new changes
     * @param response : the response returned from the server
     */
    public void showOutput(Response response);

    //TODO : discuss launch(). From my perspective, having the constructor set everything up is fine.
    // I suggest a start/launch method that gets called to have the client launch to the server.
    // This approach may need to be repeated (if they pick a existing name) and so should be able to
    // be called again without breaking the gui

    //TODO : Perhaps use a hasLaunched flag to check if it has launched and otherwise ask for name again,
    // This could be changed according to its next command response from showOutput().
    // Then the flag can be checked in getInput()

}


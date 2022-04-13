package za.co.wethinkcode.robotworlds.client;

import za.co.wethinkcode.robotworlds.protocol.Response;

/**
 * Interface for GUI's to have when run by the client
 */
public interface GUI{
    /**
     * The client will try get input from the GUI constantly to make a request to the server
     * @return The user's latest input
     * @throws NoNewInput : if the user has not given input since last asked
     */
    public String getInput() throws NoNewInput;

    /**
     * Takes info from the response to draw the new changes
     * @param response : the response returned from the server
     */
    public void showOutput(Response response);
}

class NoNewInput extends RuntimeException {
    
}
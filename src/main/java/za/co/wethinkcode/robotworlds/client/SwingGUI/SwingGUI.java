package za.co.wethinkcode.robotworlds.client.SwingGUI;

import za.co.wethinkcode.robotworlds.client.GUI;
import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;

import javax.swing.*;
import java.util.Scanner;

// TODO : This is a mock up of how the swing GUI should look in a sense
public class SwingGUI implements GUI {
    // TODO : add variables as necessary
    private String latestInput = "";
    TankWorld gui;
    private static String clientName;
    private static String robotType;
    static Scanner scanner = new Scanner(System.in);

    public SwingGUI(){
        // TODO : Start up your GUI from here, instantiate all the windows/objects you need
        //  and set up any sort of flags etc here too.
        //  I'd suggest starting up a few threads here, specifically one for getting input
        latestInput = "";

    }

    @Override
    public Request getInput() throws NoNewInput {
        // TODO : This looks like it is setup in TankWorld. Could reuse
        //  You can setup a call for the predictive movement here, or in the thread that is checking for user input
        if (!latestInput.equals("")){
            Request request = new Request("TODO", "TODO", null);
            assumeOutput(request);

        }
        throw new NoNewInput();
    }

    @Override
    public void showOutput(Response response) {
        // TODO : Use the response to update your output to have the latest info
    }

    @Override
    public String getClientName() {
        return clientName;
    }

    @Override
    public String getRobotType() {return robotType;}

    @Override
    public void setEnemyName(String enemyName) {

    }

    public void startGUI() {
        gui.start();
    }

    @Override
    public void enemyMovement(String command) {

    }

    // TODO : Add private methods as necessary
    private void assumeOutput(Request request){
        // TODO : guesses what it'll look like while it waits for the server to respond

    }
}

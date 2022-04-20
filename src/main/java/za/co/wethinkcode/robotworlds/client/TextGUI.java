package za.co.wethinkcode.robotworlds.client;

import java.util.*;

import za.co.wethinkcode.robotworlds.exceptions.NoNewInput;
import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.protocol.Response;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

/**
 * Example of how to implement GUI, by using sout and sin
 */
public class TextGUI implements GUI {
    /**
     * Used for input from user
     */
    private static final Scanner in = new Scanner(System.in);
    /**
     * Stores the name of the robot that is currently controlled by this user
     */
    String robotName;
    /**
     * Flag to check if a robot has been launched into the server successfully
     */
    boolean hasLaunched;

    /**
     * Sets the text GUI up to be ready to launch a robot.
     */
    public TextGUI(){
        hasLaunched = false;
    }

    /**
     * Takes a string input from terminal and turns it into a response object
     * @return a request made by the user
     * @throws NoNewInput
     */
    @Override
    public Request getInput() throws NoNewInput {
        // TODO : As a note, you could preemptively
        //  assume the change as input is received and output that from here
        //  Then use showOutput() to correct that guess.

        try {
            // if the robot has not launched, make request with new name and send launch request.
            if (!hasLaunched){
                // TODO : could specify kind of robot to be launched here too.
                System.out.print("Enter Robot Name : ");
                this.robotName = in.nextLine();

                System.out.print("Enter Type of Robot : ");
                String robotType = in.nextLine();
                // Hard coded for now.
                robotType = "BasicRobot";

                return new Request(
                        robotName,
                        "launch",
                        new ArrayList<String>(Collections.singletonList(robotType))
                );
            }
            // default scenario. Gets input, splits it into command and args, returns usual request
            String[] input = in.nextLine().split(" ");
            return new Request(
                    robotName,
                    input[0],
                    Arrays.asList(Arrays.copyOfRange(input, 1, input.length-1))
            );

        } catch (NoSuchElementException elevated) {
            throw new NoNewInput();
        }
    }

    /**
     * This prints out the response in a formatted way
     * @param response : the response returned from the server
     */
    @Override
    public void showOutput(Response response) {
        // checks if the server has let the robot launch, otherwise tries again.
        if (!hasLaunched && response.getCommandResponse().equals("success")){
            hasLaunched = true;
        } else if (!hasLaunched) {
            return;
        }

        // if it runs out of shield, game-over
        if (response.getRobot().getShield() == 0)
        {
            quit();
            return;
        }


        HashMap<Integer, HashMap<Integer, Character>> map = response.getMap();

        for (int x =0; x < map.size(); x++){
            for (int y = 0; y < map.get(x).size(); y++){
                System.out.print(map.get(x).get(y));

            }
            System.out.print('\n');

        }

        HashMap<String, Robot> enemyRobots = response.getEnemyRobots();
        if (enemyRobots != null){
            System.out.println("There are enemies nearby:");
            for (Robot robot : enemyRobots.values()){
                System.out.println(robot.getName() + " :");
                System.out.println("\tShield Remaining: " + robot.getShield());
                System.out.println("\tFacing: " + robot.getDirection());
            }
        }

        System.out.println(robotName +  " : " + response.getCommandResponse());
    }

    /**
     * In this case, quit either closes the client, or makes the gui reset, so that it can launch a new robot.
     */
    private void quit(){
        System.out.println("GAME OVER");
        System.out.print("Play again? (y/n) : ");
        if (in.nextLine().equalsIgnoreCase("y")){
            hasLaunched = false;
            return;
        }
        System.exit(0);
    }
}


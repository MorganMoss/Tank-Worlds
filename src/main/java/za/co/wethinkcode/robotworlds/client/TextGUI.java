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
    boolean hasLaunched, waiting;
    /**
     * This lets it check if there's changes; if there are, it will update the output
     */
    Response previousResponse;

    /**
     * Sets the text GUI up to be ready to launch a robot.
     */
    public TextGUI(){
        hasLaunched = false;
        waiting = false;
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
            if (!hasLaunched && !waiting){
                waiting = true;
                // TODO : could specify kind of robot to be launched here too.
                System.out.print("Enter Robot Name : ");
                this.robotName = in.nextLine();

                System.out.print("Enter Type of Robot : ");
                String robotType = in.nextLine();
                // TODO : Hard coded for now.
                robotType = "BasicRobot";

                return new Request(
                        robotName,
                        "launch",
                        new ArrayList<String>(Collections.singletonList(robotType))
                );
            }
            // default scenario. Gets input, splits it into command and args, returns usual request
            String[] input = in.nextLine().split(" ");
            List<String> args = new ArrayList<>();
            if (input.length > 1){
                args = Arrays.asList(Arrays.copyOfRange(input, 1, input.length-1));
            }
            return new Request(
                robotName,
                input[0],
                args
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
        if (!hasLaunched && response.getCommandResponse().equalsIgnoreCase("success")){
            previousResponse = response;
            hasLaunched = true;
        } else if (!hasLaunched) {
            System.out.println("Launch failed : " + response.getCommandResponse());
            return;
        }
        waiting = false;

        if (previousResponse.serialize().equals(response.serialize())){
            return;
        }

        previousResponse = response;

        // if it runs out of shield, game-over
        if (response.getRobot().getCurrentShield() == 0) {
            quit();
            return;
        }

        HashMap<Integer, HashMap<Integer, String>> map = response.getMap();

        for (int y = 0; y < map.get(0).size();  y++){
            for (int x =0; x < map.size(); x++){
                System.out.print("" + map.get(x).get(y).charAt(0) /*+ map.get(x).get(y).charAt(0)*/);
            }
            System.out.print('\n');
        }

        HashMap<String, Robot> enemyRobots = response.getEnemyRobots();
        if (enemyRobots != null && enemyRobots.size() > 0){
            System.out.println("There are enemies nearby:");
            for (Robot robot : enemyRobots.values()){
                System.out.println(robot.getRobotName() + " :");
                System.out.println("\tShield Remaining: " + robot.getCurrentShield());
                System.out.println("\tFacing: " + robot.getDirection());
                System.out.println("\tAbsolute Position: " + robot.getPosition());

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


package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.exceptions.RobotNotFoundException;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class LaunchCommand extends Command{

    public LaunchCommand(String argument) {
        super("launch", argument);
    }


    @Override
    public void execute(World world, String robotName) {
        world.add(new BasicRobot(robotName));

//        try {
//            world.getRobot(robotName);
//        } catch (RobotNotFoundException robotDoesNotExist) {
//        }
    }


}

package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName) {
        super(robotName);
    }


    @Override
    public void execute(World world) {
        world.add(new BasicRobot(robotName));

//        try {
//            world.getRobot(robotName);
//        } catch (RobotNotFoundException robotDoesNotExist) {
//        }
    }


}

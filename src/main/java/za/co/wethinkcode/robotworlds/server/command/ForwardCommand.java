package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.exceptions.RobotNotFoundException;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ForwardCommand extends Command{
    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public void execute(World world, String robotName) {
        try {
            world.updatePosition(robotName, 5);
        } catch (PathBlockedException ignored) {}
    }
}

package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class BackCommand extends Command{
    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public void execute(World world, String robotName) {
        try {
            world.updatePosition(robotName, 5);
        } catch (PathBlockedException ignored) {}
    }
}

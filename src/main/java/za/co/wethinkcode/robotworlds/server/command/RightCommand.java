package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class RightCommand extends Command{
    public RightCommand() {
        super("right");
    }

    @Override
    public void execute(World world, String robotName) {
        world.updateDirection(robotName, 90);
    }
}
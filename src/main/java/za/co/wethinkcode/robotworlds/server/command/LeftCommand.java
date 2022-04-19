package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class LeftCommand extends Command{
    public LeftCommand() {
        super("left");
    }

    @Override
    public void execute(World world, String robotName) {
        world.updateDirection(robotName,-90);
    }
}
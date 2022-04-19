package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class RightCommand extends Command{
    public RightCommand(String robotName) {
        super(robotName);
    }

    @Override
    public void execute(World world) {
        world.updateDirection(robotName, 90);
    }
}
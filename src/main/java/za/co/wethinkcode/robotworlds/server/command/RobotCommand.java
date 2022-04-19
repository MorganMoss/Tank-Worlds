package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class RobotCommand extends Command{
    public RobotCommand(String robotName) {
        super(robotName);
    }

    @Override
    public void execute(World world) {
        // TODO : execute command
    }
}
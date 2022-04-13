package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class RightCommand extends Command{
    public RightCommand() {
        super("right");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
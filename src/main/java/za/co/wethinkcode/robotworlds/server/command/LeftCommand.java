package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class LeftCommand extends Command{
    public LeftCommand() {
        super("left");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
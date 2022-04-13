package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class LookCommand extends Command{
    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
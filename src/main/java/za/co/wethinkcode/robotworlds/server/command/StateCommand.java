package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class StateCommand extends Command{
    public StateCommand() {
        super("state");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
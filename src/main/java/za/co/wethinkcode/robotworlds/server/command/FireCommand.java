package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class FireCommand extends Command{
    public FireCommand() {
        super("fire");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}

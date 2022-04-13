package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class DumpCommand extends Command{
    public DumpCommand() {
        super("dump");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}
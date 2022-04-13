package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class RepairCommand extends Command{
    public RepairCommand() {
        super("repair");
    }

    @Override
    public boolean execute(Robot target) {
        return false;
    }
}

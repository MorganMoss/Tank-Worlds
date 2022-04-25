package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class IdleCommand extends Command{

    public IdleCommand(String RobotName) {
        super(RobotName);
    }

    @Override
    public String execute(World world) {
        return "idle";
    }
}

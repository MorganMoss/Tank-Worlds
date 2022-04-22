package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class RepairCommand extends Command{

    public RepairCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        Thread thread = new RepairThread(world, world.getRobot(robotName));
        thread.start();
        return "Repair in progress";
    }
}

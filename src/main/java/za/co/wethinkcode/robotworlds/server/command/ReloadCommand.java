package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class ReloadCommand extends Command{

    public ReloadCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        Thread thread = new ReloadThread(world, world.getRobot(robotName));
        thread.start();
        return "Reload in progress";
    }
}

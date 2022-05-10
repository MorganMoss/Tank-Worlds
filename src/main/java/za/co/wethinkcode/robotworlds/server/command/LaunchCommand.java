package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.*;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName,String argument) {
        super(robotName,argument);
    }

    @Override
    public String execute(World world) {
        world.add(new Robot(world, robotName.toLowerCase(),argument));
        return "Success";
    }
}

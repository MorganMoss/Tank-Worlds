package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        world.add(new BasicRobot(robotName.toLowerCase()));
        return "Success";
    }


}

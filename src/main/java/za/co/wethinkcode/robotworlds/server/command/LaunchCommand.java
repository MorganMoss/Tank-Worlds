package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName,String argument) {
        super(robotName,argument);
    }

    @Override
    public String execute() {
        World.add(new Robot(robotName ,argument));
        return "Success";
    }
}

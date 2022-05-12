package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.RobotNotFoundException;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName,String argument) {
        super(robotName,argument);
    }

    @Override
    public String execute() {
        try {
            World.getRobot(robotName);
        } catch (RobotNotFoundException good){
            World.add(new Robot(robotName ,argument));
            return "Success";
        }
        return "Robot with that name already exists.";
    }
}

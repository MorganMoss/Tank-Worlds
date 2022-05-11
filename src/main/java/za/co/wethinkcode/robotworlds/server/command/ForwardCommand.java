package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.exceptions.PathBlockedException;

public class ForwardCommand extends Command{

    public ForwardCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public String execute() {
        try {
            World.updatePosition(robotName, Integer.parseInt(argument));
            World.getRobot(robotName).setStatus("normal");
            return "Success";
        } catch (PathBlockedException ignored) {
            return "Path Blocked!";
        }
    }
}

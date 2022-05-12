package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.exceptions.PathBlockedException;

public class BackCommand extends Command{

    public BackCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public String execute() {
        try {
            World.updatePosition(robotName, -Integer.parseInt(argument));
            World.getRobot(robotName).setStatus("normal");
            return "Success";
        } catch (PathBlockedException ignored) {
            return "Path Blocked";
        }
    }
}

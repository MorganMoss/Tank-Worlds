package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.server.World;

public class ForwardCommand extends Command{

    public ForwardCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public String execute(World world) {
        try {
            world.updatePosition(robotName, Integer.parseInt(argument));
            return "Success";
        } catch (PathBlockedException ignored) {
            return "Path Blocked!";
        }
    }
}

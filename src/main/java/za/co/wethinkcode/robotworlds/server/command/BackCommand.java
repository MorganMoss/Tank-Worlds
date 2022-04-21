package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.server.World;

public class BackCommand extends Command{

    public BackCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public String execute(World world) {
        try {
            world.updatePosition(robotName, 5);
        } catch (PathBlockedException ignored) {}
        return "Success";
    }
}

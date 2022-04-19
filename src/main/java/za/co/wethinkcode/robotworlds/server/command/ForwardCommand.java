package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class ForwardCommand extends Command{
    public ForwardCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public void execute(World world) {
        // TODO : execute command
    }
}

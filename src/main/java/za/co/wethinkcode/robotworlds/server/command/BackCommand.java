package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class BackCommand extends Command{

    public BackCommand(String robotName, String argument) {
        super(robotName, argument);
    }

    @Override
    public void execute(World world) {
        // TODO : execute command
    }
}

package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class StateCommand extends Command{
    public StateCommand(String robotName) {
        super(robotName);
    }

    @Override
    public void execute(World world) {
        // TODO : execute command
    }
}
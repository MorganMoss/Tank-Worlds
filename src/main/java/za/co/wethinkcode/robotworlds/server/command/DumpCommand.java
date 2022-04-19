package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class DumpCommand extends Command{
    public DumpCommand(String robotName) {
        super(robotName);
    }

    @Override
    public void execute(World world) {
        // TODO : execute command
    }
}
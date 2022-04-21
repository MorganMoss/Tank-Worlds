package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class LookCommand extends Command{
    public LookCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        // TODO : execute command
        return "Success";
    }
}
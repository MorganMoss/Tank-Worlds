package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class FireCommand extends Command{
    public FireCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        // TODO : execute command
        return "Success";
    }
}

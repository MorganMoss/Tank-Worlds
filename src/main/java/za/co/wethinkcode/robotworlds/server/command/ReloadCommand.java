package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ReloadCommand extends Command{
    public ReloadCommand() {
        super("reload");
    }

    @Override
    public void execute(World world, String robotName) {

    }
}

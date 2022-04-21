package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ReloadCommand extends Command{
    public ReloadCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        // TODO: execute command
        // pause robot 3s while reloading ammo.
        Robot robot = world.getRobot(this.robotName);

        return "Success";
    }
}

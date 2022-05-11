package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class RightCommand extends Command{
    public RightCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        World.getRobot(robotName).setStatus("normal");
        World.updateDirection(robotName, 90);
        return "Success";
    }
}
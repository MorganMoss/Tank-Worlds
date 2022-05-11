package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;

public class LeftCommand extends Command{
    public LeftCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        World.updateDirection(robotName,270);
        World.getRobot(robotName).setStatus("normal");
        return "Success";
    }
}
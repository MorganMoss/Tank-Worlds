package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ForwardCommand extends Command{
    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    @Override
    public void execute(Robot target) {
        // TODO : execute command
    }
}

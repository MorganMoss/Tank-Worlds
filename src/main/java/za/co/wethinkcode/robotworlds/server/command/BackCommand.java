package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class BackCommand extends Command{
    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public void execute(Robot target) {
        // TODO : execute command
    }
}

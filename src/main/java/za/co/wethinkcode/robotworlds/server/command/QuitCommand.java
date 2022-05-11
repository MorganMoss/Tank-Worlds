package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
public class QuitCommand extends Command{

    public QuitCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        World.remove(robotName);
        return "Quit Successful";
    }
}

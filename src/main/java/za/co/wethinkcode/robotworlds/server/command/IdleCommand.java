package za.co.wethinkcode.robotworlds.server.command;

public class IdleCommand extends Command{

    public IdleCommand(String RobotName) {
        super(RobotName);
    }

    @Override
    public String execute() {
        return "idle";
    }
}

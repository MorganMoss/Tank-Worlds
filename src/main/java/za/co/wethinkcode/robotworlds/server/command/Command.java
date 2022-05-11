package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.server.World;

import java.util.List;

public abstract class Command {
    String robotName;
    String argument;

    public abstract String execute(World world);

    public Command(String robotName){
        this.robotName = robotName.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String robotName, String argument) {
        this(robotName);
        this.argument = argument.trim();
    }

    public static Command create(Request request) {
        List<String> args = request.getArguments();
        String robotName = request.getRobotName();

        switch (request.getCommand()) {

            case "launch":
                return new LaunchCommand(robotName, args.get(0));
            case "idle":
                return new IdleCommand(robotName);
            case "forward":
                return new ForwardCommand(robotName, "1");
            case "back":
                return new BackCommand(robotName, "1");
            case "left":
                return new LeftCommand(robotName);
            case "right":
                return new RightCommand(robotName);
            case "fire":
                return new FireCommand(robotName);
            case "repair":
                return new RepairCommand(robotName);
            case "reload":
                return new ReloadCommand(robotName);
            case "look":
                return new LookCommand(robotName);
            case "stuck":
                return new StuckCommand(robotName);
            default:
                throw new IllegalArgumentException("Unsupported command: " + request);
        }
    }
}


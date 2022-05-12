package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.shared.protocols.Request;

import java.util.List;

public abstract class Command {
    String robotName;
    String argument;

    public abstract String execute();

    public Command(String robotName){
        this.robotName = robotName;
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
            case "quit":
                return new QuitCommand(robotName);
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
            case "stuck":
                return new StuckCommand(robotName);
            default:
                throw new IllegalArgumentException("Unsupported command: " + request);
        }
    }
}


package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.server.World;

import java.util.List;

public abstract class Command {
    String robotName;
    String argument;

    public abstract void execute(World world);

    public Command(String name){
        this.robotName = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public static Command create(Request request) {
        List<String> args = request.getArguments();
        String robotName = request.getRobotName();

        switch (request.getCommand()) {
            case "forward":
                return new ForwardCommand(robotName, args.get(0));
            case "back":
                return new BackCommand(robotName, args.get(0));
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
            case "state":
                return new StateCommand(robotName);
            case "dump":
                return new DumpCommand(robotName);
            case "robot":
                return new RobotCommand(robotName);
            default:
                throw new IllegalArgumentException("Unsupported command: " + request);
        }
    }
}


package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    String name;
    String argument;

    public abstract void execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public static Command create(Request request) {
        List<String> args = request.getArguments();
        if (args == null) {
            args = new ArrayList<>();
            args.add("5");
        }
        switch (request.getCommand()) {
            case "forward":
                return new ForwardCommand(args.get(0));
            case "back":
                return new BackCommand(args.get(0));
            case "left":
                return new LeftCommand();
            case "right":
                return new RightCommand();
            case "fire":
                return new FireCommand();
            case "repair":
                return new RepairCommand();
            case "reload":
                return new ReloadCommand();
            case "look":
                return new LookCommand();
            case "state":
                return new StateCommand();
            case "dump":
                return new DumpCommand();
            case "robot":
                return new RobotCommand();
            default:
                throw new IllegalArgumentException("Unsupported command: " + request);
        }
    }
}


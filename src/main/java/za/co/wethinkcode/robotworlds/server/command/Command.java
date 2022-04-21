// TODO : Waiting for Maggie and Sisipho to push their updated version
//  This goes for all commands.
package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.protocol.Request;
import za.co.wethinkcode.robotworlds.server.World;

import java.util.List;

public abstract class Command {
    String robotName;
    String argument;

    public abstract String execute(World world);

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
            case "launch":
                return new LaunchCommand(robotName);
            case "forward":
                return new ForwardCommand(robotName, "5");
            case "back":
                return new BackCommand(robotName, "5");
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
            default:
                throw new IllegalArgumentException("Unsupported command: " + request);
        }
    }
}


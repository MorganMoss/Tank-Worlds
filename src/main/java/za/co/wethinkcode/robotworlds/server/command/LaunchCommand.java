package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Sniper;
import za.co.wethinkcode.robotworlds.server.robot.bomber;
import za.co.wethinkcode.robotworlds.server.robot.machine;

import java.util.Locale;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName,String argument) {
        super(robotName,argument);
    }

    @Override
    public String execute(World world) {
        if(argument.equalsIgnoreCase("basicRobot")) {
            world.add(new BasicRobot(robotName.toLowerCase()));
        } else if (argument.equalsIgnoreCase("Sniper")) {
            world.add(new Sniper(robotName.toLowerCase()));
        }
        else if (argument.equalsIgnoreCase("Bomber")) {
            world.add(new bomber(robotName.toLowerCase()));
        }
        else if (argument.equalsIgnoreCase("Machine")) {
            world.add(new machine(robotName.toLowerCase()));
        }
        return "Success";
    }


}

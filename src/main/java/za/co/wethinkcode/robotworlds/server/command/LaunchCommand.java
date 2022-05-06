package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Sniper;
import za.co.wethinkcode.robotworlds.server.robot.Bomber;
import za.co.wethinkcode.robotworlds.server.robot.Machine;

public class LaunchCommand extends Command{

    public LaunchCommand(String robotName,String argument) {
        super(robotName,argument);
    }

    @Override
    public String execute(World world) {
        if(argument.equalsIgnoreCase("BasicRobot")) {
            world.add(new BasicRobot(robotName.toLowerCase()));
        } else if (argument.equalsIgnoreCase("Sniper")) {
            world.add(new Sniper(robotName.toLowerCase()));
        }
        else if (argument.equalsIgnoreCase("Bomber")) {
            world.add(new Bomber(robotName.toLowerCase()));
        }
        else if (argument.equalsIgnoreCase("Machine")) {
            world.add(new Machine(robotName.toLowerCase()));
        }
        return "Success";
    }


}

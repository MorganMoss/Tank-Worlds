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
        switch (argument.toLowerCase()){
            case "basicrobot":
                world.add(new BasicRobot(world, robotName.toLowerCase()));
            case "sniper":
                world.add(new Sniper(world, robotName.toLowerCase()));
            case "bomber":
                world.add(new Bomber(world, robotName.toLowerCase()));
            case "machine":
                world.add(new Machine(world, robotName.toLowerCase()));
            default:
                world.add(new BasicRobot(world, robotName.toLowerCase()));
        }
        return "Success";
    }
}

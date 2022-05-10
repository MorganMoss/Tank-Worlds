package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.Robot;

public class RepairCommand extends Command{

    public RepairCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        Thread thread = new Thread(new RepairThread(world, world.getRobot(super.robotName)));
        thread.start();
        return "Repair in progress";
    }


    public static class RepairThread implements Runnable {
        private final World world;
        private final Robot robot;

        public RepairThread(World world, Robot robot) {
            this.world = world;
            this.robot = robot;
        }

        public void run() {
            world.repair(robot);
        }
    }
}

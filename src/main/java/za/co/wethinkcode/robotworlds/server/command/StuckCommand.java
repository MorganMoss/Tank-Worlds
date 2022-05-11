package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class StuckCommand extends Command{

    public StuckCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        Thread thread = new Thread(new StuckThread(world, world.getRobot(robotName)));
        thread.start();
        return "Reload in progress";
    }

    public static class StuckThread implements Runnable {
        private final World world;
        private final Robot robot;

        public StuckThread(World world, Robot robot) {
            this.world = world;
            this.robot = robot;
        }

        public void run() {
            world.stuck(robot);
        }
    }
}

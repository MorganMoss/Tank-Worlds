package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.Robot;

public class ReloadCommand extends Command{

    public ReloadCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        Thread thread = new Thread(new ReloadThread(world, world.getRobot(robotName)));
        thread.start();
        return "Reload in progress";
    }

    public static class ReloadThread implements Runnable {
        private final World world;
        private final Robot robot;

        public ReloadThread(World world, Robot robot) {
            this.world = world;
            this.robot = robot;
        }

        public void run() {
            world.reload(robot);
        }
    }
}

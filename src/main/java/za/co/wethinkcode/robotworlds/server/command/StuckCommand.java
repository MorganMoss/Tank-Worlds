package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;


public class StuckCommand extends Command{

    public StuckCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        Thread thread = new Thread(new StuckThread(World.getRobot(robotName)));
        thread.start();
        return "Reload in progress";
    }

    public static class StuckThread implements Runnable {
        private final Robot robot;

        public StuckThread(Robot robot) {
            this.robot = robot;
        }

        public void run() {
            World.stuck(robot);
        }
    }
}

package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;

public class ReloadCommand extends Command{

    public ReloadCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        Thread thread = new Thread(new ReloadThread(World.getRobot(robotName)));
        thread.start();
        return "Reload in progress";
    }

    public static class ReloadThread implements Runnable {
        private final Robot robot;

        public ReloadThread(Robot robot) {
            this.robot = robot;
        }

        public void run() {
            World.reload(robot);
        }
    }
}

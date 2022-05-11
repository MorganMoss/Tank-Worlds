package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;

public class RepairCommand extends Command{

    public RepairCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute() {
        Thread thread = new Thread(new RepairThread(World.getRobot(super.robotName)));
        thread.start();
        return "Repair in progress";
    }


    public static class RepairThread implements Runnable {
        private final Robot robot;

        public RepairThread(Robot robot) {
            this.robot = robot;
        }

        public void run() {
            World.repair(robot);
        }
    }
}

package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class RepairThread extends Thread{

    World world;
    Robot robot;

    public RepairThread(World world, Robot robot) {
        this.world = world;
        this.robot = robot;
    }

    public void run() {
        world.repair(robot);
    }
}

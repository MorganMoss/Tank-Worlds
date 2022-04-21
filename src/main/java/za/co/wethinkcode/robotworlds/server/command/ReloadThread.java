package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class ReloadThread extends Thread{

    World world;
    Robot robot;

    public ReloadThread(World world, Robot robot) {
        this.world = world;
        this.robot = robot;
    }

    public void run() {
        world.reload(robot);
    }
}

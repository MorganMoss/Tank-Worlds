package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class FireCommand extends Command {
    public FireCommand(String robotName) {
        super(robotName);
    }

    @Override
    public String execute(World world) {
        // TODO : execute command
//        int distance = 5;
//        Robot robot = world.getRobot(robotName);
//        world.get
//
//
//        Position initialBullet = robot.getPosition();
//        Position finalBullet = new Position(0,0);//add distance depending on the direction.
//
//        if (robot.getDirection().getAngle() == 0) {
//            finalBullet = new Position(initialBullet.getX(), initialBullet.getY() + distance);
//        } else if (robot.getDirection().getAngle() == 90) {
//            finalBullet = new Position(initialBullet.getX() + distance, initialBullet.getY());
//
//        } else if (robot.getDirection().getAngle() == 180) {
//            finalBullet = new Position(initialBullet.getX(), initialBullet.getY() - distance);
//
//        } else if (robot.getDirection().getAngle() == 270) {
//            finalBullet = new Position(initialBullet.getX() - distance, initialBullet.getY());
//        }
//
//        if (!world.pathBlocked(initialBullet, finalBullet)) {
//            return "miss";
//        } else {
//
//        }
    return null;
    }
}

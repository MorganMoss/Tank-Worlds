package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.PathBlockedResponse;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.collisionObjects.Bullet;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.util.ArrayList;
import java.util.List;

public class FireCommand extends Command{

    public FireCommand(String robotName, String argument) {
        super(robotName, argument);
    }

//    @Override
//    public String execute(World world) {
//        Robot robot = world.getRobot(robotName);
//        Bullet bullet = new Bullet(robot);
//        world.add(bullet);
//        return "Success";
//    }




    @Override
    public String execute(World world) {
        Robot robot = world.getRobot(robotName);
        if (robot.getCurrentAmmo() > 0) {
            robot.decreaseAmmo();
            List<Position> bulletList = getBulletList(robot);
            Position finalBullet = bulletList.get(bulletList.size() - 1);
            PathBlockedResponse result = world.pathBlocked(robot.getPosition(), finalBullet);

            switch (result) {
                case MISS:
                    return "Miss";
                case OBSTACLE_HIT:
                    return "Hit obstacle";
                case ENEMY_HIT:
                    for (Robot enemy : world.getEnemies(robot).values()) {
                        if (isEnemyHit(robot, enemy)) {
                            enemy.decreaseShield();
                            return "Hit enemy : " + enemy;
                        }
                    }
            }
        }
        return "No ammo";
    }


    private boolean isEnemyHit(Robot robot, Robot enemy) {
        List<Position> bulletList = getBulletList(robot);
        for (Position bulletPosition : bulletList) {
            if (bulletPosition == enemy.getPosition()) {
                return true;
            }
        }
        return false;
    }


    public List<Position> getBulletList(Robot robot) {

        int distance = Integer.parseInt(argument);
        Position bulletPosition = new Position(0,0);
        List<Position> bulletList = new ArrayList<>();

        for (int i = 1; i <= distance; i++) {
            if (robot.getDirection().getAngle() == 0) {
                bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() + i);
            } else if (robot.getDirection().getAngle() == 90) {
                bulletPosition = new Position(robot.getPosition().getX() + i, robot.getPosition().getY());
            } else if (robot.getDirection().getAngle() == 180) {
                bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() - i);
            } else if (robot.getDirection().getAngle() == 270) {
                bulletPosition = new Position(robot.getPosition().getX() - i, robot.getPosition().getY());
            }
            bulletList.add(bulletPosition);
        }
        return bulletList;
    }
}

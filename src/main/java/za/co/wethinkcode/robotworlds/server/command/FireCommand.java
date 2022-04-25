package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.PathBlockedResponse;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.util.ArrayList;
import java.util.List;

public class FireCommand extends Command {
    //TODO: implement command


    public FireCommand(String robotName, String argument) {
        super(robotName, argument);
    }

//    @Override
//    public String execute(World world) {
//        Robot robot = world.getRobot(robotName);
//        if (robot.getCurrentAmmo() > 0) {
//            robot.decreaseAmmo();
//            for (Robot enemy : world.getRobots().values()) {
//                if (isEnemyHit(robot, enemy)) {
//                    enemy.decreaseShield();
//                    return "Hit";
//                }
//            }
//            return "Miss";
//        } else {
//            return "No ammo";
//        }
//    }

    @Override
    public String execute(World world) {
        Robot robot = world.getRobot(robotName);
        robot.decreaseAmmo();
        PathBlockedResponse result = world.pathBlocked(robot.getPosition(),getFinalBullet(robot));

        switch (result) {
            case OBSTACLE_HIT:
                return "Hit obstacle";
            case ENEMY_HIT:
                for (Robot enemy : world.getRobots().values()) {
                    if (isEnemyHit(robot,enemy)) {
                        enemy.decreaseShield();
                        return "Hit enemy : " + enemy;
                    }
                }
            case MISS:
                return "Miss";
            default:
                return "Error";
        }
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


    private Position getFinalBullet(Robot robot) {
        int distance = robot.getFiringDistance();
        Position bulletPosition = new Position(0,0);

        if (robot.getDirection().getAngle() == 0) {
            bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() + distance);
        } else if (robot.getDirection().getAngle() == 90) {
            bulletPosition = new Position(robot.getPosition().getX() + distance, robot.getPosition().getY());
        } else if (robot.getDirection().getAngle() == 180) {
            bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() - distance);
        } else if (robot.getDirection().getAngle() == 270) {
            bulletPosition = new Position(robot.getPosition().getX() - distance, robot.getPosition().getY());
        }
        return bulletPosition;
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

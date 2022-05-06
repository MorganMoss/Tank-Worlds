package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import java.util.List;

public class FireCommand extends Command{

    public FireCommand(String robotName, String argument) {
            super(robotName, argument);
        }

        @Override
        public String execute(World world) {
            Robot robot = world.getRobot(robotName);
            if (robot.getCurrentAmmo() > 0) {
                robot.decreaseAmmo();
                List<Position> bulletList = world.getBulletList(robot);
                Position finalBullet = bulletList.get(bulletList.size() - 1);
                String result = world.pathBlocked(robot, robot.getPosition(), finalBullet);
                String[] resultList = result.split(" ");
                if (resultList[1].equals("enemy")) {
                    Robot enemy = world.getRobot(resultList[2]);
                    enemy.decreaseShield();
                }
                return result;
            }
            return "no_ammo";
        }
}

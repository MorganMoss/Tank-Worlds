package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;

import java.util.List;

public class FireCommand extends Command{

    public FireCommand(String robotName) {
            super(robotName);
        }

        @Override
        public String execute() {
            Robot robot = World.getRobot(robotName);

            World.getRobot(robotName).setStatus("normal");

            if (robot.getCurrentAmmo() > 0) {
                robot.decreaseAmmo();

                List<Position> bulletList = World.getBulletList(robot);

                Position finalBullet = bulletList.get(bulletList.size() - 1);

                String result = World.pathBlocked(robot, finalBullet);
                String[] resultList = result.split(" ");
                if (resultList.length > 1){
                    if (resultList[1].equals("enemy")) {
                        Robot enemy = World.getRobot(resultList[2]);
                        enemy.decreaseShield();
                    }
                    return result;
                }
            }
            return "no_ammo";
        }
}

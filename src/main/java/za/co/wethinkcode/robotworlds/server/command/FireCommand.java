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

                if (result.contains("hit enemy ")){
                        Robot enemy = World.getRobot(result.replace("hit enemy ", ""));
                        enemy.decreaseShield();
                    }
                    return result;
                }

            return "no_ammo";
        }
}

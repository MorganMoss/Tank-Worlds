package za.co.wethinkcode.robotworlds.server.command;

import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;

import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.toRadians;

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

//                List<Position> bulletList = World.getBulletList(robot);
//
//                Position finalBullet = bulletList.get(bulletList.size() - 1);
//

                Position bulletDestination =  new Position(
                        (int) (robot.getPosition().getX()
                                + round(robot.getFiringDistance() * sin(toRadians(robot.getDirection().getAngle())))),
                        (int) (robot.getPosition().getY()
                                + round(robot.getFiringDistance() * cos(toRadians(robot.getDirection().getAngle()))))
                );

                String result = World.pathBlocked(robot, bulletDestination);

                if (result.contains("hit enemy ")){
                        Robot enemy = World.getRobot(result.replace("hit enemy ", ""));
                        enemy.decreaseShield();
                    }
                    return result;
                }

            return "no_ammo";
        }
}

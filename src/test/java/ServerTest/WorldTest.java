package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WorldTest {
    @Test
    public void getRobot() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot1 = new Robot(world, "homer","sniper");
        Robot robot2 = new Robot(world, "marge","sniper");
        world.add(robot1);
        world.add(robot2);
        assertEquals(robot1, world.getRobot("homer"));

    }
}

package ServerTest.RobotTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicRobotTest {
    @Test
    public void testBasicRobot(){
        World world = new World(new BasicMap(new Position(100,100)));
        BasicRobot basicRobot = new BasicRobot(world, "Hal");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(5,basicRobot.getRange());
        assertEquals(3,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(10,basicRobot.getFiringDistance());
        assertEquals(10,basicRobot.getVisibilityDistance());



    }
}

package ServerTest.RobotTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicRobotTest {
    @Test
    public void testBasicRobot(){
        BasicRobot basicRobot = new BasicRobot("Hal");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(5,basicRobot.getRange());
        assertEquals(3,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(10,basicRobot.getFiringDistance());
        assertEquals(25,basicRobot.getVisibilityDistance());



    }
}

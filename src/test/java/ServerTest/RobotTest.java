package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.shared.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTest {
    @Test
    public void testDefaultRobot(){
        Robot testRobot = new Robot("Hal","tank");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(testRobot.getMaxAmmo(),testRobot.getCurrentAmmo());
        assertEquals(testRobot.getMaxShield(),testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(10,testRobot.getFiringDistance());
        assertEquals(12,testRobot.getVisibilityDistance());
    }

    @Test
    public void testSniper(){
        Robot testRobot = new Robot("Hal","sniper");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(testRobot.getMaxAmmo(),testRobot.getCurrentAmmo());
        assertEquals(testRobot.getMaxShield(),testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(18,testRobot.getFiringDistance());
        assertEquals(20,testRobot.getVisibilityDistance());
    }
    @Test
    public void testMachine(){
        Robot testRobot = new Robot( "Hal","machine");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(testRobot.getMaxAmmo(),testRobot.getCurrentAmmo());
        assertEquals(testRobot.getMaxShield(),testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(4,testRobot.getFiringDistance());
        assertEquals(6,testRobot.getVisibilityDistance());
    }
    @Test
    public void testBomber(){
        Robot testRobot = new Robot( "Hal","bomber");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(testRobot.getMaxAmmo(),testRobot.getCurrentAmmo());
        assertEquals(testRobot.getMaxShield(),testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(8,testRobot.getFiringDistance());
        assertEquals(10,testRobot.getVisibilityDistance());
    }
}

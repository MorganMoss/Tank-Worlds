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
        assertEquals(5,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(3,testRobot.getFiringDistance());
        assertEquals(10,testRobot.getVisibilityDistance());
    }

    @Test
    public void testSniper(){
        Robot testRobot = new Robot("Hal","sniper");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(3,testRobot.getCurrentAmmo());
        assertEquals(1,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(9,testRobot.getFiringDistance());
        assertEquals(10,testRobot.getVisibilityDistance());
    }
    @Test
    public void testMachine(){
        Robot testRobot = new Robot( "Hal","machine");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(20,testRobot.getCurrentAmmo());
        assertEquals(2,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(2,testRobot.getFiringDistance());
        assertEquals(10,testRobot.getVisibilityDistance());
    }
    @Test
    public void testBomber(){
        Robot testRobot = new Robot( "Hal","bomber");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(10,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(5,testRobot.getFiringDistance());
        assertEquals(10,testRobot.getVisibilityDistance());
    }
}

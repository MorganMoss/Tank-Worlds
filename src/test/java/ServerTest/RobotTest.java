package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTest {
    @Test
    public void testBasicRobot(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot basicRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(15,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(50,basicRobot.getFiringDistance());
        assertEquals(9,basicRobot.getVisibilityDistance());
    }

    @Test
    public void testSniper(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot basicRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(15,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(50,basicRobot.getFiringDistance());
        assertEquals(9,basicRobot.getVisibilityDistance());
    }
    @Test
    public void testMachine(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot basicRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(15,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(50,basicRobot.getFiringDistance());
        assertEquals(9,basicRobot.getVisibilityDistance());
    }
    @Test
    public void testBomber(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot basicRobot = new Robot(world, "Hal","bomber");
        assertEquals("Hal",basicRobot.getRobotName());
        assertEquals(15,basicRobot.getCurrentAmmo());
        assertEquals(3,basicRobot.getCurrentShield());
        assertEquals(0,basicRobot.getDeaths());
        assertEquals(0,basicRobot.getKills());
        assertEquals(50,basicRobot.getFiringDistance());
        assertEquals(9,basicRobot.getVisibilityDistance());
    }
}

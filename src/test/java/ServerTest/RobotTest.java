package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTest {
    @Test
    public void testDefaultRobot(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot testRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(15,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(50,testRobot.getFiringDistance());
        assertEquals(9,testRobot.getVisibilityDistance());
    }

    @Test
    public void testSniper(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot testRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(15,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(50,testRobot.getFiringDistance());
        assertEquals(9,testRobot.getVisibilityDistance());
    }
    @Test
    public void testMachine(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot testRobot = new Robot(world, "Hal","bleh");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(15,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(50,testRobot.getFiringDistance());
        assertEquals(9,testRobot.getVisibilityDistance());
    }
    @Test
    public void testBomber(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot testRobot = new Robot(world, "Hal","bomber");
        assertEquals("Hal",testRobot.getRobotName());
        assertEquals(15,testRobot.getCurrentAmmo());
        assertEquals(3,testRobot.getCurrentShield());
        assertEquals(0,testRobot.getDeaths());
        assertEquals(0,testRobot.getKills());
        assertEquals(50,testRobot.getFiringDistance());
        assertEquals(9,testRobot.getVisibilityDistance());
    }
}

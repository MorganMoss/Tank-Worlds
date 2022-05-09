package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Sniper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTest {
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
    @Test
    public void testSniper(){
        World world = new World(new BasicMap(new Position(100,100)));
        Sniper sniper = new Sniper(world, "Hal");
        assertEquals("Hal",sniper.getRobotName());
        assertEquals(5,sniper.getRange());
        assertEquals(1,sniper.getCurrentAmmo());
        assertEquals(3,sniper.getCurrentShield());
        assertEquals(0,sniper.getDeaths());
        assertEquals(0,sniper.getKills());
        assertEquals(5,sniper.getFiringDistance());
        assertEquals(12,sniper.getVisibilityDistance());



    }
}

package ClientTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Pit;
import za.co.wethinkcode.robotworlds.shared.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObstacleTest {
    @Test
    public void testBrick(){
        Brick brick = new Brick(new Position(20,10));
        assertEquals(20,brick.getX());
        assertEquals(10,brick.getY());
        assertEquals(0,brick.getRange());
        assertEquals(25,brick.getSize());
    }

    @Test
    public void testPit(){
        Pit pit = new Pit(new Position(0,0));
        assertEquals(0,pit.getX());
        assertEquals(0,pit.getY());
        assertEquals(0,pit.getRange());
        assertEquals(25,pit.getSize());

    }

}

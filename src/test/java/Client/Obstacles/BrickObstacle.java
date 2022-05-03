package Client.Obstacles;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.server.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrickObstacle {
    @Test
    public void testBrick(){
        Brick brick = new Brick(new Position(20,10));
        assertEquals(20,brick.getX());
        assertEquals(10,brick.getY());
        assertEquals(0,brick.getRange());
        assertEquals(25,brick.getSize());


    }

}

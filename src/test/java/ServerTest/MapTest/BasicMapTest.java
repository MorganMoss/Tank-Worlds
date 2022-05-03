package ServerTest.MapTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;

import static org.junit.jupiter.api.Assertions.*;

public class BasicMapTest {
    @Test
    public void testBasicMap(){
        BasicMap basicMap = new BasicMap(new Position(2,3));
        assertEquals(2,basicMap.getMapSize().getX());
        assertEquals(3,basicMap.getMapSize().getY());
        assertEquals("square",basicMap.getObstacles().get(0).getShape());
        assertFalse(basicMap.getObstacles().get(0).isPositionBlocked(new Position(7, 13)));

    }

}

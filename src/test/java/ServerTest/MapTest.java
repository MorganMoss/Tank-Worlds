package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    @Test
    public void testBasicMap(){
        Map basicMap = new BasicMap(new Position(2,3));
        assertEquals(2,basicMap.getMapSize().getX());
        assertEquals(3,basicMap.getMapSize().getY());
        assertEquals("square",basicMap.getObstacles().get(0).getShape());
        assertFalse(basicMap.getObstacles().get(0).isPositionBlocked(new Position(7, 13)));
    }
}

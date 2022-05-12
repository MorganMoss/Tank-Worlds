package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.shared.Direction;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {
    @Test
    public void testDirectionSouth(){
        Direction direction = Direction.SOUTH;
        assertEquals(180.0,direction.getAngle());
        assertEquals("SOUTH",direction.name());
    }
    @Test
    public void testDirectionNorth(){
        Direction direction = Direction.NORTH;
        assertEquals(0.0,direction.getAngle());
        assertEquals("NORTH",direction.name());
    }
    @Test
    public void testDirectionEast(){
        Direction direction = Direction.EAST;
        assertEquals(90.0,direction.getAngle());
        assertEquals("EAST",direction.name());
    }
    @Test
    public void testDirectionWest(){
        Direction direction = Direction.WEST;
        assertEquals(270.0,direction.getAngle());
        assertEquals("WEST",direction.name());
    }
}

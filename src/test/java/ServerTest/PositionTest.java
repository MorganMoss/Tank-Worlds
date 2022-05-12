package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.shared.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    @Test
    public void testPosition(){
        Position position = new Position(2,5);
        assertEquals(2,position.getX());
        assertEquals(5,position.getY());
        assertEquals(true,position.isIn(new Position(2,5),new Position(2,5)));
        assertEquals(false,position.isIn(new Position(3,5),new Position(9,15)));

    }
}

package Client.Obstacles;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Pit;
import za.co.wethinkcode.robotworlds.server.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class pitObstacle {
    @Test
    public void tesPit(){
        Pit pit = new Pit(new Position(0,0));
        assertEquals(0,pit.getX());
        assertEquals(0,pit.getY());
        assertEquals(0,pit.getRange());
        assertEquals(25,pit.getSize());

    }
}

package ClientTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankWorldTest {
    @Test
    public void testTankWorld() {
        TankWorld tankWorld = new TankWorld();
        assertEquals(0, tankWorld.getX());
        assertEquals(0, tankWorld.getY());
    }
}

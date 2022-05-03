package Client.Projectiles;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Bullet;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Bullets {
    @Test
    public void testBullet() {
        Bullet bullet = new Bullet();
        assertEquals(0,bullet.getX());
        assertEquals(0,bullet.getY());
        assertEquals(0,bullet.getRange());
        assertEquals(0,bullet.getSize());

    }

}

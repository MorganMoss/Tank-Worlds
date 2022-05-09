package ClientTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Bullet;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles.Shell;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileTest {
    @Test
    public void testBullet() {
        Bullet bullet = new Bullet();
        assertEquals(0,bullet.getX());
        assertEquals(0,bullet.getY());
        assertEquals(0,bullet.getRange());
        assertEquals(0,bullet.getSize());
    }

    @Test
    public void testShell() {
        Shell shell = new Shell();
        assertEquals(0,shell.getX());
        assertEquals(0,shell.getTankX());
        assertEquals(0,shell.getY());
        assertEquals(0,shell.getTankY());
        assertEquals(0,shell.getRange());
        assertEquals(10,shell.getSize());
        assertEquals(true,shell.reachedRange(Direction.Up));
        assertEquals(true,shell.reachedRange(Direction.Down));
        assertEquals(true,shell.reachedRange(Direction.Left));
        assertEquals(true,shell.reachedRange(Direction.Right));



    }

}

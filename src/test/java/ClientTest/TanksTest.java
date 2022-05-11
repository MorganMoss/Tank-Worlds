package ClientTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Enemy;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TanksTest {
    @Test
    public void testDirectionUp(){
        Direction direction=Direction.Up;
        assertEquals("Left",direction.getLeft().toString());
        assertEquals("Right",direction.getRight().toString());
        assertEquals("Up",direction.name());
        assertEquals(1,direction.ordinal());
    }
    @Test
    public void testDirectionDown(){
        Direction direction=Direction.Down;
        assertEquals("Right",direction.getLeft().toString());
        assertEquals("Left",direction.getRight().toString());
        assertEquals("Down",direction.name());
        assertEquals(3,direction.ordinal());
    }
    @Test
    public void testDirectionLeft(){
        Direction direction=Direction.Left;
        assertEquals("Down",direction.getLeft().toString());
        assertEquals("Up",direction.getRight().toString());
        assertEquals("Left",direction.name());
        assertEquals(0,direction.ordinal());
    }
    @Test
    public void testDirectionRight(){
        Direction direction=Direction.Right;
        assertEquals("Up",direction.getLeft().toString());
        assertEquals("Down",direction.getRight().toString());
        assertEquals("Right",direction.name());
        assertEquals(2,direction.ordinal());
    }
    @Test
    public  void testEnemy(){
        Enemy enemy = new Enemy();
        assertEquals("Enemy1",enemy.getTankName());
        assertEquals(5,enemy.getTankHealth());
        assertEquals(5,enemy.getAmmo());
        assertEquals("Up",enemy.getDirection().toString());
        assertEquals(0,enemy.getRange());
        assertEquals(40,enemy.getSize());

    }
    @Test
    public  void testPlayer(){
        Player player = new Player("sniper","hal");
        assertEquals("hal",player.getTankName());
        assertEquals(3,player.getTankHealth());
        assertEquals(5,player.getAmmo());

        assertEquals("Up",player.getDirection().toString());
        assertEquals(125,player.getRange());
        assertEquals(40,player.getSize());

    }

}

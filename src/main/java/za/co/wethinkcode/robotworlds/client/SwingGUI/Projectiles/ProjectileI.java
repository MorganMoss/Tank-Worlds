package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

import java.awt.*;

public interface ProjectileI extends WorldObject {
    void discharge(Tank tank);
    void strike();
    void project(Direction direction);
    void draw(Graphics g);
    boolean isHitting(WorldObject object);
    String getSpriteName();
    int getSize();
}

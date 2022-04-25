package za.co.wethinkcode.robotworlds.client.SwingUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Enemy;
import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingUI.WorldObject;

import java.awt.*;
import java.util.ArrayList;

public interface ProjectileI extends WorldObject {
    void discharge(Tank tank);
    void strike();
    void project(Direction direction);
    void draw(Graphics g);
    boolean isHitting(WorldObject object);
    String getSpriteName();
    int getSize();
}

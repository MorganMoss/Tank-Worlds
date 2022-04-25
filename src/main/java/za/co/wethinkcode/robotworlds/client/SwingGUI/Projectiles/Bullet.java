package za.co.wethinkcode.robotworlds.client.SwingGUI.Projectiles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Tank;
import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;

import java.awt.*;

public class Bullet extends Projectile{
    int range;

    public void discharge(Tank tank) {}

    @Override
    public void strike() {}


    public void project(Direction direction) {}

    @Override
    public void draw(Graphics g) {}

    @Override
    public int getSize() {
        return super.size;
    }

    @Override
    public boolean isHitting(WorldObject object) {
        return false;
    }

    @Override
    public boolean reachedRange(Direction direction) {
        return false;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getRange() {
        return this.range;
    }

}

package za.co.wethinkcode.robotworlds.collisionObjects;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;

import java.awt.*;

public class Bullet implements CollisionObject {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void draw(Graphics g) {

    }
}

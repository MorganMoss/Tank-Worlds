package za.co.wethinkcode.robotworlds.collisionObjects;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;

import java.awt.*;

public interface CollisionObject {

    int getX();
    int getY();

    Direction getDirection();

    void draw(Graphics g);
}

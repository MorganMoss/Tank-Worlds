package za.co.wethinkcode.robotworlds.server.collisionObjects;



import za.co.wethinkcode.robotworlds.server.Direction;

import java.awt.*;

public interface CollisionObject {

    int getX();
    int getY();

    Direction getDirection();

    void draw(Graphics g);
}

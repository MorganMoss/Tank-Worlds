package za.co.wethinkcode.robotworlds.client.SwingUI;

import za.co.wethinkcode.robotworlds.client.SwingUI.Tanks.Direction;

import java.awt.*;

public interface WorldObject {
    //getters
    int getX();
    int getY();

    Direction getDirection();
    int getRange();
    void draw(Graphics g);
    int getSize();

}

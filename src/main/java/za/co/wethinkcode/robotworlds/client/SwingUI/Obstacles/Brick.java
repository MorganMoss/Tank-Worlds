package za.co.wethinkcode.robotworlds.client.SwingUI.Obstacles;

import za.co.wethinkcode.robotworlds.client.SwingUI.HelperMethods;
import za.co.wethinkcode.robotworlds.server.Position;

import java.awt.*;

public class Brick extends Obstacle{
    private final int x;
    private final int y;

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    public Brick(Position centrePosition) {
        super(centrePosition);
        this.x = centrePosition.getX();
        this.y = centrePosition.getY();
    }

    public void spawn(Graphics g) {
        g.drawImage(HelperMethods.getImage("brick.png"), x, y, null);
    }

}

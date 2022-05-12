package za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.awt.*;

public class Pit extends Obstacle{
    private static final Position centrePosition = null;
    private String sprite;
    private final int x;
    private final int y;

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public int getSize() {
        return super.size;
    }

    public Pit(Position centrePosition) {
        super(centrePosition);
        this.x = centrePosition.getX();
        this.y = centrePosition.getY();
    }
    static Image pit = HelperMethods.getImage("crater.png");
    static Image resizedPit = pit.getScaledInstance(25,25, Image.SCALE_DEFAULT);
    public void draw(Graphics g) {
        g.drawImage(resizedPit, x, y, null);
    }
}

package za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.TankWorld;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.awt.*;

public class Brick extends Obstacle{
    private final int x;
    private final int y;

    @Override
    public int getX(){return this.x;}
    @Override
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

    public Brick(Position centrePosition) {
        super(centrePosition);
        this.x = centrePosition.getX();
        this.y = centrePosition.getY();
        this.size = TankWorld.getX_scale();
    }

    static Image brick = HelperMethods.getImage("brick.png");
    static Image resizedBrick = brick.getScaledInstance(TankWorld.getX_scale()+1,TankWorld.getY_scale()+1, Image.SCALE_DEFAULT);

    public void draw(Graphics g) {
        g.drawImage(resizedBrick, x, y, null);
        if (TankWorld.getShowBoundaries()) {
            g.setColor(Color.red);
            g.drawRect(x, y, super.getSize(), super.getSize());
        }

    }

}

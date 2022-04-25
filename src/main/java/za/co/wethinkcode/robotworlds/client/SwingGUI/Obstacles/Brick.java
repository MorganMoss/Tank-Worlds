package za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles;
import za.co.wethinkcode.robotworlds.client.SwingGUI.HelperMethods;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Tanks.Direction;
import za.co.wethinkcode.robotworlds.server.Position;
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
    }

    static Image brick = HelperMethods.getImage("brick.png");
    static Image resizedBrick = brick.getScaledInstance(25,25, Image.SCALE_DEFAULT);
    public void draw(Graphics g) {g.drawImage(resizedBrick, x, y, null);}

}

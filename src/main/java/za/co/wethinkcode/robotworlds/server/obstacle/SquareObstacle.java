package za.co.wethinkcode.robotworlds.server.obstacle;

import za.co.wethinkcode.robotworlds.shared.Position;

public class SquareObstacle extends Obstacle{

    public SquareObstacle(int size, Position topLeft) {
        super("square", new Position(size,size), topLeft);
    }

    @Override
    public boolean isPositionBlocked(Position position) {
        Position topLeft = super.getPosition();
        int x = topLeft.getX() + super.getSize().getX();
        int y = topLeft.getY() - super.getSize().getY();
        Position bottomRight = new Position(x,y);

        return position.isIn(topLeft, bottomRight);
    }
}

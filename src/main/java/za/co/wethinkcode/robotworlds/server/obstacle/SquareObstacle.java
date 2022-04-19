package za.co.wethinkcode.robotworlds.server.obstacle;

import za.co.wethinkcode.robotworlds.server.Position;

public class SquareObstacle extends Obstacle{

    public SquareObstacle(int size, Position position) {
        super("square", new Position(size,size), position);
    }

    @Override
    public boolean isPositionBlocked(Position position) {
        Position bottomLeft = super.getPosition();
        int x = bottomLeft.getX() + super.getSize().getX();
        int y = bottomLeft.getY() + super.getSize().getY();
        Position topRight = new Position(x,y);

        return position.isIn(bottomLeft, topRight);
    }
}

package za.co.wethinkcode.robotworlds.server.obstacle;

import za.co.wethinkcode.robotworlds.shared.Position;

public abstract class Obstacle {
    private final String shape;
    private final Position size;
    private final Position topLeft;

    public Obstacle(String shape, Position size, Position topLeft) {
        this.shape = shape;
        this.size = size;
        this.topLeft = topLeft;
    }

    public String getShape() {return shape;}

    public Position getSize() {
        return size;
    }

    public Position getPosition() {
        return topLeft;
    }

    public abstract boolean isPositionBlocked(Position position);

    @Override
    public String toString() {
        return String.format("- At position %s (to %d,%d)", topLeft.toString(), topLeft.getX() + size.getX(), topLeft.getY() - size.getY());
    }

}

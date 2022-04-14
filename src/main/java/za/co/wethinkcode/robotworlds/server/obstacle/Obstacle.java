package za.co.wethinkcode.robotworlds.server.obstacle;

import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Obstacle {
    private final String shape;
    private final Position size;
    private final Position position;

    public Obstacle(String shape, Position size, Position position) {
        this.shape = shape;
        this.size = size;
        this.position = position;
    }

    public String getShape() {
        return shape;
    }

    public Position getSize() {
        return size;
    }


    public Position getPosition() {
        return position;
    }

    public abstract boolean isPositionBlocked(Position position);

}

package za.co.wethinkcode.robotworlds.server.obstacle;

import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Obstacle {
    private final String shape;
    private final int height;
    private final int width;
    private final Position position;

    public Obstacle(String shape, int height, int width, Position position) {
        this.shape = shape;
        this.height = height;
        this.width = width;
        this.position = position;
    }

    public String getShape() {
        return shape;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getPosition() {
        return position;
    }

    public abstract boolean isPositionBlocked(Position position);

}

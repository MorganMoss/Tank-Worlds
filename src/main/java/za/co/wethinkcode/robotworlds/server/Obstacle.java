package za.co.wethinkcode.robotworlds.server;

abstract class Obstacle {
    private final String shape;
    private final int size;
    private final Position centrePosition;

    public Obstacle(String shape, int size, Position centrePosition) {
        this.shape = shape;
        this.size = size;
        this.centrePosition = centrePosition;
    }

    public boolean isPositionBlocked(Position position) {
        return false;
    }

    public void imprint() {}
}

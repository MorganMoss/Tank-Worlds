package za.co.wethinkcode.robotworlds;

abstract class Obstacle {
    private final shape;
    private final size;
    private final centrePosition;

    abstract boolean isPositionBlocked(Position position);

    public void imprint() {}
}

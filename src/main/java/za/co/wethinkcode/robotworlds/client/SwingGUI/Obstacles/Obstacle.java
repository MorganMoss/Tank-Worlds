package za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles;

import za.co.wethinkcode.robotworlds.server.Position;

public abstract class Obstacle {
    private final Position centrePosition;

    public Obstacle(Position centrePosition) {
        this.centrePosition = centrePosition;
    }

    public boolean isPositionBlocked(Position position) {
        return false;
    }

    public void spawn() {}
}

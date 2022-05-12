package za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles;

import za.co.wethinkcode.robotworlds.client.SwingGUI.WorldObject;
import za.co.wethinkcode.robotworlds.shared.Position;

public abstract class Obstacle implements WorldObject {
    private final Position centrePosition;
    protected int size = 25;

    public Obstacle(Position centrePosition) {
        this.centrePosition = centrePosition;
    }

    public boolean isPositionBlocked(Position position) {
        return false;
    }

    public void spawn() {}
    @Override
    public int getSize() {
        return 25;
    }
}

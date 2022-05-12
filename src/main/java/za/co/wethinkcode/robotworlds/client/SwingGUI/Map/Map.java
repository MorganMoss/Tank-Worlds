package za.co.wethinkcode.robotworlds.client.SwingGUI.Map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;

import java.util.List;

public abstract class Map {
    public abstract List<Obstacle> getObstacles();
}

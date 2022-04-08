package za.co.wethinkcode.robotworlds.server;

import java.util.List;

abstract class Map {
    private final Position mapSize;
    private List<Obstacle> obstacles;

    public Map(Position mapSize) {
        this.mapSize = mapSize;
    }

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public Position getMapSize() {
        return this.mapSize;
    }
}

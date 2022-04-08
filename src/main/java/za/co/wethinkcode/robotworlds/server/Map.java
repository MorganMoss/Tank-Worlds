package za.co.wethinkcode.robotworlds;

import java.util.List;

abstract class Map {
    private final int mapSize;
    List<Obstacle> obstacles;

    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public int getMapSize() {
        return this.mapSize;
    }
}

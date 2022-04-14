package za.co.wethinkcode.robotworlds.server.map;

import java.util.ArrayList;
import java.util.List;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;

public class BasicMap implements Map{
    private final Position size;

    public BasicMap(Position size) {
        this.size = size;
    }

    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        Obstacle obstacle = new SquareObstacle(10, new Position(-50,-50));
        obstacleList.add(obstacle);
        return obstacleList;
    }

    public Position getMapSize() {
        return this.size;
    }

}

package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class BasicMap implements Map{
    private final Position size;

    public BasicMap(Position size) {
        this.size = size;
    }

    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        obstacleList.add(new SquareObstacle(3, new Position(0,0)));
        obstacleList.add(new SquareObstacle(3, new Position(0,-5)));
        obstacleList.add(new SquareObstacle(3, new Position(5,-5)));
        return obstacleList;
    }
    public Position getMapSize() {
        return this.size;
    }

}

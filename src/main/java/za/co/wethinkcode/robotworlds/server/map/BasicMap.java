package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.ArrayList;
import java.util.List;

public class BasicMap extends Map{
    public BasicMap() {
        super(new Position(100,100));
    }

    public BasicMap(Position size) {
        super(size);
    }

    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        obstacleList.add(new SquareObstacle(3, new Position(0,0)));
        obstacleList.add(new SquareObstacle(3, new Position(0,-9)));
        obstacleList.add(new SquareObstacle(3, new Position(8,-9)));
        return obstacleList;
    }


}

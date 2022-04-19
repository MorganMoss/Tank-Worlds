package za.co.wethinkcode.robotworlds.server.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        int randomValue = random.nextInt(9) + 1;
        for(int i = 1;i<=randomValue ;i++) {
            int x = random.nextInt(200) -100;
            int y = random.nextInt(400) -200;
            System.out.println();
            Obstacle obstacle = new SquareObstacle(10, new Position(x,y));
            obstacleList.add(obstacle);
        }

        return obstacleList;
    }
    public Position getMapSize() {
        return this.size;
    }

}

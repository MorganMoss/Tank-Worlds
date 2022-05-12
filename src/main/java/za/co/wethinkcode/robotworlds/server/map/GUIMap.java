package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.ArrayList;
import java.util.List;

public class GUIMap extends Map{
    public GUIMap(){
        super(new Position(600,600));
    }

    public GUIMap(Position size) {
        super(size);
    }

    public List<Obstacle> getObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        for(int x=-300;x<300;x+=25){
            Obstacle xBrick = new SquareObstacle(25,new Position(x,300));
            obstacleList.add(xBrick);
        }
        for(int y=-200;y<300;y+=25){
            Obstacle yBrick = new SquareObstacle(25, new Position(0,y));
            obstacleList.add(yBrick);
        }
        Obstacle worldBrick = new SquareObstacle(25, new Position(-200,100));
        Obstacle worldBrick2 = new SquareObstacle(25, new Position(150,-100));
        obstacleList.add(worldBrick);
        obstacleList.add(worldBrick2);

        obstacleList.add(new SquareObstacle(3, new Position(0,0)));

        return obstacleList;
    }
}

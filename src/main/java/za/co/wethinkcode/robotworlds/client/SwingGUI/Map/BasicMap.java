package za.co.wethinkcode.robotworlds.client.SwingGUI.Map;

import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Brick;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.client.SwingGUI.Obstacles.Pit;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.ArrayList;
import java.util.List;

public class BasicMap {

    public static List<Obstacle> getObstacles() {
        List<Obstacle> obstacleList = new ArrayList<>();
        for(int i=0;i<600;i+=25){
            Brick xBrick = new Brick(new Position(i,0));
            obstacleList.add(xBrick);
        }
        for(int i=0;i<400;i+=25){
            Brick yBrick = new Brick(new Position(300,i));
            obstacleList.add(yBrick);
        }

        Pit onePit = new Pit(new Position(400,300));
        obstacleList.add(onePit);
        return obstacleList;
    }

}

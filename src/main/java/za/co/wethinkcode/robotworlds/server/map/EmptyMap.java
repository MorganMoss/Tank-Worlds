package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class EmptyMap extends Map{
    @Override
    public List<Obstacle> getObstacles() {
        return new ArrayList<>();
    }
}

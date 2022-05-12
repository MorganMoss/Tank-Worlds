package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.ArrayList;
import java.util.List;

public class EmptyMap extends Map{
    public EmptyMap() {
        super(new Position(100,100));
    }
    @Override
    public List<Obstacle> getObstacles() {
        return new ArrayList<>();
    }
}

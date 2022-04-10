package za.co.wethinkcode.robotworlds.server.map;

import java.util.List;

import za.co.wethinkcode.robotworlds.server.Obstacle;
import za.co.wethinkcode.robotworlds.server.Position;

public interface Map {
    /**
     * Makes a list of obstacles to be used in a world
     * @return a list of obstacles
     */
    List<Obstacle> getObstacles();


    /**
     * Gets the size of the map
     * @return the size of the map
     */
    Position getMapSize();

}

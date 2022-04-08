package src.main.java.za.co.wethinkcode.robotworlds.server.map;

import java.util.List;

import src.main.java.za.co.wethinkcode.robotworlds.server.Obstacle;
import src.main.java.za.co.wethinkcode.robotworlds.server.Position;

public interface Map {
    /**
     * Makes a list of obstacles to be used in a world
     * @return a list of obstacles
     */
    public List<Obstacle> getObstacles();


    /**
     * Gets the size of the map
     * @return the size of the map
     */
    public Position getMapSize();

}

package za.co.wethinkcode.robotworlds.server.map;

import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.shared.Position;

import java.util.List;

public abstract class Map {

    protected static Position size;
    public Map(){

    }

    public Map(Position size){
        Map.size = size;
    }

    public static void setSize(Position size) {
        BasicMap.size = size;
    }

    /**
     * Gets the size of the map
     * @return the size of the map
     */
    public Position getMapSize() {
        return size;
    }

    /**
     * Makes a list of obstacles to be used in a world
     * @return a list of obstacles
     */
    public abstract List<Obstacle> getObstacles();



}

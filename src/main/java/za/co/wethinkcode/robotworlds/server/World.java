package za.co.wethinkcode.robotworlds.server;

import java.util.HashMap;
import java.util.List;

import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import static java.lang.Math.*;

public class World {
    private final Position mapSize;
    private List<Robot> robots;
    private final HashMap<Integer, HashMap<Integer, Character>> map;

    /**
     * Constructor for world
     * @param map : the map that has gives a list of obstacles for this world to use.
     */
    public World(Map map) {
        this.mapSize = map.getMapSize();

        List<Obstacle> obstacleList = map.getObstacles();

        this.map = new HashMap<>();
        for (int x = round(-mapSize.getX()/2.0f); x <= round(mapSize.getX()/2.0f); x++) {
            HashMap<Integer, Character> row = new HashMap<>();

            for (int y = round(-mapSize.getY()/2.0f); y <= round(mapSize.getY()/2.0f); y++) {
                for (Obstacle obstacle : obstacleList){
                    if (obstacle.isPositionBlocked(new Position(x,y))){
                        row.putIfAbsent(y, 'X'); //closed space
                        break;
                    }

                    row.putIfAbsent(y, ' '); //open space
                }
            }

            this.map.putIfAbsent(x, row);
        }
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void add(Robot robot) {
        robots.add(robot);
    }

    public void remove(Robot robot) {
        robots.remove(robot);
    }


    public void updatePosition(Robot robot, int steps) {}

    public void updateDirection(Robot robot, int degrees) {}


    public void fire(Robot robot) {}

    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param relativeCenter : The position to look from
     * @return : a grid of data representing the relative view from this position
     */
    public HashMap<Integer, HashMap<Integer, Character>> look(Position relativeCenter) {
        int distance = 10; //hardcoded for now

        int current_x = 0;
        HashMap<Integer, HashMap<Integer, Character>> result = new HashMap<>();

        for (int x = relativeCenter.getX() - distance; x <= relativeCenter.getX() + distance; x++) {
            HashMap<Integer, Character> row = new HashMap<>();
            int current_y = 0;

            for (int y = relativeCenter.getY() - distance; y <= relativeCenter.getY() + distance; y++) {
                row.putIfAbsent(current_y, map.getOrDefault(x, new HashMap<>()).getOrDefault(y, 'X'));
                current_y ++;
            }

            result.putIfAbsent(current_x, row);
            current_x++;
        }

        return result;
    }

    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param robot : The robot that is used as a reference to look from
     * @return : a grid of data representing the relative view from this position
     */
    public HashMap<Integer, HashMap<Integer, Character>> look(Robot robot) {
       return look(robot.getPosition());
    }

    public void pause(Robot robot, int duration) {}

    public void repair(Robot robot) {}

    public void reload(Robot robot) {}
}

package za.co.wethinkcode.robotworlds.server;

import java.util.List;

import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class World {
    private final Position mapSize;
    private final int repairTime;
    private List<Robot> robots;
    private List<Integer>[][] grid;


    public World(Map map, int repairTime) {
        this.repairTime = repairTime;
        this.mapSize = map.getMapSize();
        List<Obstacle> obstacleList = map.getObstacles();
        // TODO : take obstacles and turn them into a grid of integers
        grid = null;
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

    public void look(Robot robot) {}

    public void pause(Robot robot, int duration) {}

    public void repair(Robot robot) {}

    public void reload(Robot robot) {}
}

package za.co.wethinkcode.robotworlds;

import java.util.List;

public class World {
    private final List<Obstacle> obstacleList;
    private final Bounds;
    private final RepairTime;
    private List<Robot> robots;
    private List<Integer>[][] grid;

    public void add(Robot robot) {}

    public void remove(Robot robot) {}

    public void updatePosition(Robot robot, int steps) {}

    public void updateDirection(Robot robot, int degrees) {}

    public void fire(Robot robot) {}

    public void look(Robot robot) {}

    public void pause(Robot robot, int duration) {}

    public void repair(Robot robot) {}

    public void reload(Robot robot) {}
}

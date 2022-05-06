package za.co.wethinkcode.robotworlds.server;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.exceptions.RobotNotFoundException;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class World {
    private final HashMap<String, Robot> robots;
    private final HashMap<Integer, HashMap<Integer, String>> map; //"X"," ",<RobotName>
    private final Position mapSize;

    /**
     * Constructor for world
     * @param map : the map that has gives a list of obstacles for this world to use.
     */
    public World(Map map) {
        mapSize = map.getMapSize();

        List<Obstacle> obstacleList = map.getObstacles();

        this.map = new HashMap<>();
        for (int x = round(-mapSize.getX()/2.0f); x <= round(mapSize.getX()/2.0f); x++) { //ROWS
            HashMap<Integer, String> row = new HashMap<>();

            for (int y = round(-mapSize.getY()/2.0f); y <= round(mapSize.getY()/2.0f); y++) { //COLUMNS
                for (Obstacle obstacle : obstacleList){
                    if (obstacle.isPositionBlocked(new Position(x,y))){
                        row.putIfAbsent(y, "X"); //closed space
                        break;
                    }
                    row.putIfAbsent(y, " "); //open space
                }
            }
            this.map.putIfAbsent(x, row);
        }
        this.robots = new HashMap<>();
    }

    public Robot getRobot(String name) throws RobotNotFoundException {
        Robot robot = robots.get(name.toLowerCase());
        if (robot == null){
            throw new RobotNotFoundException();
        }
        return robot;
    }

    public HashMap<String, Robot> getRobots() {
        return this.robots;
    }

    public void add(Robot robot) {
        Position launchPosition;
        Random random = new Random();
        do {
            int x = random.nextInt(mapSize.getX()-100+1) - (mapSize.getX()-50)/2;
            int y = random.nextInt(mapSize.getY()-100+1) - (mapSize.getY()-50)/2;
            launchPosition = new Position(x,y);
        } while(!map.get(launchPosition.getX()).get(launchPosition.getY()).equals(" "));
        robot.setPosition(launchPosition);
        robot.setDirection(0);
        robots.put(robot.getRobotName(), robot);
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        map.get(x).put(y, robot.getRobotName());
    }

    public void remove(String robotName) {
        this.remove(getRobot(robotName));
    }

    public void remove(Robot robot) {
        map.get(robot.getPosition().getX()).put(robot.getPosition().getY(), " ");
        robots.remove(robot.getRobotName());
    }

    /**
     * Looks at the path and sees if it's all an open path
     * @param position : the old position
     * @param newPosition : the new position
     * @return true if there's something in the way, false if movement is unimpeded
     */
    public String pathBlocked(Robot robot, Position position, Position newPosition) {
        final int low;
        final int high;
        String response = "";

        if (position.getX() == newPosition.getX()){
            final int x = newPosition.getX();

            if (position.getY() > newPosition.getY()) {
                low = newPosition.getY();
                high = position.getY()-1;
            } else {
                low = position.getY()+1;
                high = newPosition.getY();
            }

            for (int y = low; y <= high; y++){
                if (!map.get(x).get(y).equals(" ")){
                    if (map.get(x).get(y).equals("X")) {
                        return String.format("hit obstacle %d1 %d2",x,y);
                    } else {
                        for (Robot enemy : getEnemies(robot).values()) {
                            if (isEnemyHit(robot, enemy)) {
                                return String.format("hit enemy %s", enemy.getRobotName());
                            }
                        }
                    }
                }
            }
        } else {
            final int y = newPosition.getY();

            if (position.getX() > newPosition.getX()) {
                low = newPosition.getX();
                high = position.getX() - 1;
            } else {
                low = position.getX() + 1;
                high = newPosition.getX();
            }

            for (int x = low; x <= high; x++){
                if (!map.get(x).get(y).equals(" ")){
                    if (map.get(x).get(y).equals("X")) {
                        return String.format("hit obstacle %d1 %d2", x, y);
                    } else {
                        for (Robot enemy : getEnemies(robot).values()) {
                            if (isEnemyHit(robot, enemy)) {
                                return String.format("hit enemy %s", enemy.getRobotName());
                            }
                        }
                        return response;
                    }
                }
            }
        }
        return "miss";
    }

    /**
     * Tries to move the robot a number of steps forward or backward relative to its direction.
     * @param robotName : the robot being moved
     * @param steps : the distance the robot moves
     * @throws PathBlockedException : thrown if the movement is not possible
     */
    public void updatePosition(String robotName, int steps) throws PathBlockedException{
        Robot robot = getRobot(robotName);
        Position newPosition =  new Position(
                (int) (robot.getPosition().getX() + round(steps * sin(toRadians(robot.getDirection().getAngle())))),
                (int) (robot.getPosition().getY() + round(steps * cos(toRadians(robot.getDirection().getAngle()))))
        );
        if (pathBlocked(robot, robot.getPosition(), newPosition).equals("miss")) {
            map.get(robot.getPosition().getX()).put(robot.getPosition().getY(), " ");
            map.get(newPosition.getX()).put(newPosition.getY(), robotName);
            robot.setPosition(newPosition);
        } else {
            throw new PathBlockedException();
        }
    }

    public void updateDirection(String robotName, int degrees) {
        Robot robot = getRobot(robotName);
        robot.setDirection((int) (robot.getDirection().getAngle()) + degrees);
    }

    //TODO
    public void fire(Robot robot) {}

    // TODO : add an argument for viewDistance.
    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param relativeCenter : The position to look from
     * @return : a grid of data representing the relative view from this position
     */
    public HashMap<Integer, HashMap<Integer, String>> look(Position relativeCenter) {
        int distance = 600;//hardcoded for now 10

        int current_x = 0;
        HashMap<Integer, HashMap<Integer, String>> result = new HashMap<>();

        for (int x = relativeCenter.getX() - distance; x <= relativeCenter.getX() + distance; x++) {
            HashMap<Integer, String> row = new HashMap<>();
            int current_y = 0;

            for (int y = relativeCenter.getY() - distance; y <= relativeCenter.getY() + distance; y++) {
                row.putIfAbsent(current_y, map.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X"));
                current_y ++;
            }

            result.putIfAbsent(current_x, row);
            current_x++;
        }

        return result;
    }

    // TODO : Have this give the base method the viewDistance of the robot
    //  (Do the to do for the base method first)
    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param robot : The robot that is used as a reference to look from
     * @return : a grid of data representing the relative view from this position
     */
    public HashMap<Integer, HashMap<Integer, String>> look(Robot robot) {
       return look(robot.getPosition());
    }

    //TODO
    public void pause(Robot robot, int duration) {}

    public void repair(Robot robot) {
        robot.resetShield();
    }

    public void reload(Robot robot) {
        robot.resetAmmo();
    }

    public HashMap<String, Robot> getEnemies(Robot robot) {
        HashMap<String, Robot> enemies = new HashMap<>();
        for (Robot robotObj : robots.values()) {
            if (robotObj != robot) try {
                enemies.put(robotObj.getRobotName(),robotObj);
            } catch (RobotNotFoundException ignored) {}
        }
        return enemies;
    }

    private boolean isEnemyHit(Robot robot, Robot enemy) {
        List<Position> bulletList = getBulletList(robot);
        for (Position bulletPosition : bulletList) {
            if (bulletPosition == enemy.getPosition()) {
                return true;
            }
        }
        return false;
    }

    public List<Position> getBulletList(Robot robot) {

        int distance = robot.getFiringDistance();
        Position bulletPosition = new Position(0,0);
        List<Position> bulletList = new ArrayList<>();

        for (int i = 1; i <= distance; i++) {
            if (robot.getDirection().getAngle() == 0) {
                bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() + i);
            } else if (robot.getDirection().getAngle() == 90) {
                bulletPosition = new Position(robot.getPosition().getX() + i, robot.getPosition().getY());
            } else if (robot.getDirection().getAngle() == 180) {
                bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() - i);
            } else if (robot.getDirection().getAngle() == 270) {
                bulletPosition = new Position(robot.getPosition().getX() - i, robot.getPosition().getY());
            }
            bulletList.add(bulletPosition);
        }
        return bulletList;
    }
}

package za.co.wethinkcode.robotworlds.server;

import static java.lang.Math.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import za.co.wethinkcode.robotworlds.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.exceptions.RobotNotFoundException;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;

public class World {
    private static int visibilityDistance;
    private static int repairTime;
    private static int reloadTime;
    private static int maxShield;

    private final HashMap<String, Robot> robots;
    private final HashMap<Integer, HashMap<Integer, String>> worldMap; //"X"," ",<RobotName>
    private final Map loadedMap;

    /**
     * Constructor for world
     * @param map : the map that has gives a list of obstacles for this world to use.
     */
    public World(Map map) {

        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            visibilityDistance = Integer.parseInt(properties.getProperty("visibility"));
            repairTime = Integer.parseInt(properties.getProperty("repairTime"));
            reloadTime = Integer.parseInt(properties.getProperty("reloadTime"));
            maxShield = Integer.parseInt(properties.getProperty("maxShield"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");;
        }

        this.loadedMap = map;
        List<Obstacle> obstacleList = map.getObstacles();

        this.worldMap = new HashMap<>();
        for (int x = round(-map.getMapSize().getX()/2.0f); x <= round(map.getMapSize().getX()/2.0f); x++) { //ROWS
            HashMap<Integer, String> row = new HashMap<>();

            for (int y = round(-map.getMapSize().getY()/2.0f); y <= round(map.getMapSize().getY()/2.0f); y++) { //COLUMNS
                for (Obstacle obstacle : obstacleList){
                    if (obstacle.isPositionBlocked(new Position(x,y))){
                        row.putIfAbsent(y, "X"); //closed space
                        break;
                    }
                    row.putIfAbsent(y, " "); //open space
                }
            }
            this.worldMap.putIfAbsent(x, row);
        }
        this.robots = new HashMap<>();
    }

    /**
     * Get a robot object from its name
     * @param name : the robot's name
     * */
    public Robot getRobot(String name) throws RobotNotFoundException {
        Robot robot = robots.get(name);
        if (robot == null){
            throw new RobotNotFoundException();
        }
        return robot;
    }


    public HashMap<String, Robot> getRobots() {
        return this.robots;
    }

    public HashMap<Integer, HashMap<Integer, String>> getWorldMap() {
        return this.worldMap;
    }

    public Position getMapSize() {
        return loadedMap.getMapSize();
    }

    public static int getVisibilityDistance() {
        return visibilityDistance;
    }

    public static int getMaxShield() {
        return maxShield;
    }

    /**
     * Launch a robot at a random position in the world
     * @param robot : the robot to be added
     * */
    public void add(Robot robot) {
        robots.put(robot.getRobotName(), robot);
        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        worldMap.get(x).put(y, robot.getRobotName());
    }

    public void remove(String robotName) {
        this.remove(getRobot(robotName));
    }

    public void remove(Robot robot) {
        worldMap.get(robot.getPosition().getX()).put(robot.getPosition().getY(), " ");
        robots.remove(robot.getRobotName());
    }

    /**
     * Looks at the path and sees if it's all an open path
     * @param robot : the robot that wants to move
     * @param newPosition : the new position
     * @return  "miss" : if no obstacles in the way
     *          "hit obstacle {x} {y}" : if an obstacle is hit
     *          "hit enemy {enemyName}" : if an enemy is hit
     */
    public String pathBlocked(Robot robot, Position newPosition) {
        final int low;
        final int high;
        String response = "";
        Position position = robot.getPosition();

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
                if (!worldMap.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X").equals(" ")){
                    if (worldMap.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X").equals("X")) {
                        return String.format("hit obstacle %d %d",x,y);
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
                if (!worldMap.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X").equals(" ")){
                    if (worldMap.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X").equals("X")) {
                        return String.format("hit obstacle %d %d", x, y);
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
        if (pathBlocked(robot, newPosition).equals("miss")) {
            setRobotPosition(robot, newPosition);
        } else {
            throw new PathBlockedException();
        }
    }

    /**
     * Sets a robot's position without first checking whether the position or path is blocked.
     * @param robot : the robot whose position will be set
     * @param newPosition : new Position of robot
     * */
    public void setRobotPosition(Robot robot, Position newPosition) {
        worldMap.get(robot.getPosition().getX()).put(robot.getPosition().getY(), " ");
        worldMap.get(newPosition.getX()).put(newPosition.getY(), robot.getRobotName());
        robot.setPosition(newPosition);
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
    public HashMap<Integer, HashMap<Integer, String>> look(Position relativeCenter, int distance) {
        int current_x = 0;
        HashMap<Integer, HashMap<Integer, String>> result = new HashMap<>();

        for (int x = relativeCenter.getX() - distance; x <= relativeCenter.getX() + distance; x++) {
            HashMap<Integer, String> row = new HashMap<>();
            int current_y = 0;

            for (int y = relativeCenter.getY() - distance; y <= relativeCenter.getY() + distance; y++) {
                row.putIfAbsent(current_y, worldMap.getOrDefault(x, new HashMap<>()).getOrDefault(y, "X"));
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
       return look(robot.getPosition(), robot.getVisibilityDistance());
    }

    //TODO
    public void pause(Robot robot, int duration) {}

    /**
     * Pause the robot while doing repairs
     * @param robot : the robot to repair
     * */
    public void repair(Robot robot) {
        robot.setStatus("repair");
        robot.setPaused(true);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.resetShield();
                robot.setPaused(false);
            }
        }, repairTime*1000L);
    }

    /**
     * Pause the robot while reloading
     * @param robot : the robot to reload
     * */
    public void reload(Robot robot) {
        robot.setStatus("reload");
        robot.setPaused(true);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.resetAmmo();
                robot.setPaused(false);
            }
        }, reloadTime*1000L);

    }


    /**
     * Returns all robots in the world except the given robot
     * @param robot : the robot that wants info on its enemies
     * */
    public HashMap<String, Robot> getEnemies(Robot robot) {
        HashMap<String, Robot> enemies = new HashMap<>();
        for (Robot robotObj : robots.values()) {
            if (robotObj != robot) try {
                enemies.put(robotObj.getRobotName(),robotObj);
            } catch (RobotNotFoundException ignored) {}
        }
        return enemies;
    }

    /**
     * Checks whether an enemy was hit by the robot
     * @param robot : the robot that fired the shot
     * @param enemy : the robot being shot at
     * */
    private boolean isEnemyHit(Robot robot, Robot enemy) {
        List<Position> bulletList = getBulletList(robot);
        for (Position bulletPosition : bulletList) {
            if (bulletPosition.equals(enemy.getPosition())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Compiles a list consisting of all the positions the bullet will
     * travel to if world objects are ignored
     * @param robot : the robot that fired the shot
     * @return list of bullet positions
     * */
    public List<Position> getBulletList(Robot robot) {

        int distance = robot.getFiringDistance();
        Position bulletPosition = new Position(0,0);
        List<Position> bulletList = new ArrayList<>();

        for (int i = 1; i <= distance; i++) {
            int angle = (int) robot.getDirection().getAngle();
            switch (angle) {
                case 0:
                    bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() + i);
                    break;
                case 90:
                    bulletPosition = new Position(robot.getPosition().getX() + i, robot.getPosition().getY());
                    break;
                case 180:
                    bulletPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY() - i);
                    break;
                case 270:
                    bulletPosition = new Position(robot.getPosition().getX() - i, robot.getPosition().getY());
                    break;
            }
            bulletList.add(bulletPosition);
        }
        return bulletList;
    }
}

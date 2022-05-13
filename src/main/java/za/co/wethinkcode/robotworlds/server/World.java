package za.co.wethinkcode.robotworlds.server;

import za.co.wethinkcode.robotworlds.server.map.*;
import za.co.wethinkcode.robotworlds.server.map.Map;
import za.co.wethinkcode.robotworlds.server.obstacle.Obstacle;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;
import za.co.wethinkcode.robotworlds.shared.exceptions.PathBlockedException;
import za.co.wethinkcode.robotworlds.shared.exceptions.RobotNotFoundException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.Math.*;

public class World {
    /**
     * The largest distance a robot can see in this world
     */
    private static final int visibilityDistance =  Integer.parseInt(getConfigProperty("visibility"));
    /**
     * The repair time for each robot in this world
     */
    private static final int repairTime = Integer.parseInt(getConfigProperty("reloadTime"));
    /**
     * The reloading time for each robot in this world
     */
    private static final int reloadTime = Integer.parseInt(getConfigProperty("maxShield"));
    /**
     * The highest the maximum shield can be for each robot in this world
     */
    private static final int maxShield = Integer.parseInt(getConfigProperty("repairTime"));
    /**
     * The map loaded by config to be used for it's obstacles
     */
    private static Map loadedMap = getMap();
    /**
     *  The World Map. It contains shorthand references ot obstacles, open spaces and robots as a grid.
     *      <li> Obstacle : "X"</li>
     *      <li> Open : " "</li>
     *      <li> Robot : "RobotName" </li>
     */
    private static final HashMap<Integer, HashMap<Integer, String>> worldMap = constructWorldMap();
    /**
     * The robots currently active in this world
     */
    private static final HashMap<String, Robot> robots = new HashMap<>();

    public static void resetMap(){
        loadedMap = getMap();
        worldMap.clear();
        worldMap.putAll(constructWorldMap());
    }

    /**
     * Gets a generic property from the server config file
     * @param property the property key
     * @return the property value
     */
    public static String getConfigProperty(String property){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String value = properties.getProperty(property);
            if (value != null){
                return value;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        System.out.println("Property " + property + " is not defined in the config");
        System.exit(1);
        return "";
    }

    /**
     * This should take the config file and get a map
     * @return a map that will be used to define the world's size, and it's obstacles
     */
    private static Map getMap() {
        String[] mapSize;
        String map = getConfigProperty("map");
        mapSize = getConfigProperty("mapSize").split(",");
        Map.setSize(new Position(Integer.parseInt(mapSize[0]), Integer.parseInt(mapSize[1])));

        switch (map){
            case "MazeMap":
                return new MazeMap();
            case "GUIMap":
                return new GUIMap();
            case "BasicMap":
                return new BasicMap();
            case "EmptyMap":
                return new EmptyMap();
        }

        System.out.println("Error: Bad map property");
        System.exit(1);
        return null;
    }

    /**
     * Constructs the grid of the World Map from the loaded map
     */
    public static HashMap<Integer, HashMap<Integer, String>> constructWorldMap(){
        List<Obstacle> obstacleList = loadedMap.getObstacles();

        HashMap<Integer, HashMap<Integer, String>> constructedMap = new HashMap<>();

        for (int x = -loadedMap.getMapSize().getX()/2; x <= round(loadedMap.getMapSize().getX()/2.0f); x++) { //ROWS
            HashMap<Integer, String> row = new HashMap<>();

            for (int y = round(-loadedMap.getMapSize().getY()/2.0f); y <= round(loadedMap.getMapSize().getY()/2.0f); y++) { //COLUMNS
                for (Obstacle obstacle : obstacleList){
                    if (obstacle.isPositionBlocked(new Position(x,y))){
                        row.put(y, "X"); //closed space
                        break;
                    }
                }

                row.putIfAbsent(y, " "); //open space
            }
            constructedMap.putIfAbsent(x, row);
        }
        return constructedMap;
    }

    /**
     * Get a robot object from its name
     * @param name : the robot's name
     * */
    public static Robot getRobot(String name) throws RobotNotFoundException {
        Robot robot = robots.get(name);
        if (robot == null){
            throw new RobotNotFoundException();
        }
        return robot;
    }

    /**
     * Gets the list of active robots
     * @return the list of active robots
     */
    public static HashMap<String, Robot> getRobots() {
        return robots;
    }

    /**
     * Get the world map in its current state
     * @return the world map in its current state
     */
    public static HashMap<Integer, HashMap<Integer, String>> getWorldMap() {
        return worldMap;
    }

    /**
     * Gets the width and height of the map loaded
     * @return a position holding those values as x and y respectively
     */
    public static Position getMapSize() {
        return loadedMap.getMapSize();
    }


    /**
     * Gets a valid starting position for a robot
     * @return a position of an open space
     */
    public static Position getLaunchPosition() {
        Position launchPosition;
        Random random = new Random();
        do {
            int limit = -50;
            int x = random.nextInt(getMapSize().getX()+1+limit) - (getMapSize().getX()+limit/2)/2;
            int y = random.nextInt(getMapSize().getY()+1+limit) - (getMapSize().getY()+limit/2)/2;
            launchPosition = new Position(x,y);
        } while(!worldMap.get(launchPosition.getX()).get(launchPosition.getY()).equals(" "));
        return launchPosition;
    }

    /**
     * Launch a robot at a random position in the world
     * @param robot : the robot to be added
     * */
    public static void add(Robot robot) {
        //limits imposed by server config
        if (robot.getMaxShield() > maxShield){
            robot.setMaxShield(maxShield);
        }

        robot.setPosition(getLaunchPosition());
        robots.put(robot.getRobotName(), robot);

        int x = robot.getPosition().getX();
        int y = robot.getPosition().getY();
        worldMap.get(x).put(y, robot.getRobotName());
    }

    /**
     * Handles removal of a robot from the world using their name
     * @param robotName : of robot to be removed
     */
    public static void remove(String robotName) {
        remove(getRobot(robotName));
    }

    /**
     * Handles removal of a robot from the world
     * @param robot : to be removed
     */
    public static void remove(Robot robot) {
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
    public static String pathBlocked(Robot robot, Position newPosition) {
        String response = "";
        Position position = robot.getPosition();

        final int interval;

        if (position.getX() == newPosition.getX()){
            final int x = newPosition.getX();

            if (position.getY() > newPosition.getY()) {
                interval = -1;
            } else {
                interval = 1;
            }

            for (int y = position.getY() + interval; y != newPosition.getY() + interval; y += interval){
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
                interval = -1;
            } else {
                interval = 1;
            }

            for (int x = position.getX() + interval; x != newPosition.getX() + interval; x += interval){
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
    public static void updatePosition(String robotName, int steps) throws PathBlockedException{
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
    public static void setRobotPosition(Robot robot, Position newPosition) {
        worldMap.get(robot.getPosition().getX()).put(robot.getPosition().getY(), " ");
        worldMap.get(newPosition.getX()).put(newPosition.getY(), robot.getRobotName());
        robot.setPosition(newPosition);
    }

    /**
     * Rotates robot by the degrees given
     * @param robotName robot ot be rotated
     * @param degrees the number of degrees (in 90 degree intervals)
     */
    public static void updateDirection(String robotName, int degrees) {
        Robot robot = getRobot(robotName);
        robot.setDirection((int) (robot.getDirection().getAngle()) + degrees);
    }


    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param relativeCenter : The position to look from
     * @return : a grid of data representing the relative view from this position
     */
    public static HashMap<Integer, HashMap<Integer, String>> look(Position relativeCenter, int distance) {
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

    /**
     * Makes a hashmap of hashmaps going from 0 to 2*viewDistance
     * contains characters representing obstacles, open spaces and robots
     * @param robot : The robot that is used as a reference to look from
     * @return : a grid of data representing the relative view from this position
     */
    public static HashMap<Integer, HashMap<Integer, String>> look(Robot robot) {
        //limits imposed by server config
        return look(robot.getPosition(), (
                Math.min(robot.getVisibilityDistance(), visibilityDistance))
        );
    }

    /**
     * Pause the robot while doing repairs
     * @param robot : the robot to repair
     * */
    public static void repair(Robot robot) {
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
    public static void reload(Robot robot) {
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
     * Pause the robot while doing repairs
     * @param robot : the robot to repair
     * */
    public static void stuck(Robot robot) {
        robot.setPaused(true);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.setPaused(false);
            }
        }, reloadTime*1000);
    }


    /**
     * Returns all robots in the world except the given robot
     * @param robot : the robot that wants info on its enemies
     * */
    public static HashMap<String, Robot> getEnemies(Robot robot) {
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
    private static boolean isEnemyHit(Robot robot, Robot enemy) {
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
    public static List<Position> getBulletList(Robot robot) {

        int distance = robot.getFiringDistance();
        Position bulletPosition1 = new Position(0,0);
        Position bulletPosition2 = new Position(0,0);
        Position bulletPosition3 = new Position(0,0);
        List<Position> bulletList = new ArrayList<>();

        for (int i = 1; i <= distance; i++) {
            int angle = (int) robot.getDirection().getAngle();
            switch (angle) {
                case 0:
                    bulletPosition1 = new Position(robot.getPosition().getX(), robot.getPosition().getY() + i);
                    bulletPosition2 = new Position(robot.getPosition().getX()-1, robot.getPosition().getY() + i);
                    bulletPosition3 = new Position(robot.getPosition().getX()+1, robot.getPosition().getY() + i);
                    break;
                case 90:
                    bulletPosition1 = new Position(robot.getPosition().getX() + i, robot.getPosition().getY());
                    bulletPosition2 = new Position(robot.getPosition().getX() + i, robot.getPosition().getY()+1);
                    bulletPosition3 = new Position(robot.getPosition().getX() + i, robot.getPosition().getY()-1);
                    break;
                case 180:
                    bulletPosition1 = new Position(robot.getPosition().getX(), robot.getPosition().getY() - i);
                    bulletPosition2 = new Position(robot.getPosition().getX()+1, robot.getPosition().getY() - i);
                    bulletPosition3 = new Position(robot.getPosition().getX()-1, robot.getPosition().getY() - i);
                    break;
                case 270:
                    bulletPosition1 = new Position(robot.getPosition().getX() - i, robot.getPosition().getY());
                    bulletPosition2 = new Position(robot.getPosition().getX() - i, robot.getPosition().getY()+1);
                    bulletPosition3 = new Position(robot.getPosition().getX() - i, robot.getPosition().getY()-1);
                    break;
            }
            bulletList.add(bulletPosition1);
            bulletList.add(bulletPosition2);
            bulletList.add(bulletPosition3);
        }
        return bulletList;
    }
}

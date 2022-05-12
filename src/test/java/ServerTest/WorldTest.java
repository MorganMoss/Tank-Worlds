package ServerTest;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Robot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    //setup is to modify the static world
    private static String oldMap;
    @BeforeAll
    static void changeConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            oldMap = properties.getProperty("map");
            properties.setProperty("map", "BasicMap");
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
            properties.store(fileOutputStream, "Test config override");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        World.resetMap();
        showWorld();
    }

    @AfterAll
    static void revertConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty("map", oldMap);
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
            properties.store(fileOutputStream, "Test config override");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    static void showWorld(){
        HashMap<Integer, HashMap<Integer, String>> map = World.look(new Position(0,0),
                (World.getMapSize().getX() > World.getMapSize().getY()) ?
                        (World.getMapSize().getX()/2+2): World.getMapSize().getY()/2+2);
        for (int y = map.get(0).size()-1; y >= 0;  y--){
            for (int x =0; x < map.size(); x++){
                try{
                    System.out.print("" + map.get(x).get(y).charAt(0) /*+ map.get(x).get(y).charAt(0)*/);
                } catch (NullPointerException odd) {
                    System.out.println(x + "," + y);
                }
            }
            System.out.print('\n');
        }
    }

    @Test
    public void addRobot() {
        Robot robot1 = new Robot("homer","sniper");
        Robot robot2 = new Robot("marge","sniper");
        World.add(robot1);
        World.add(robot2);
        assertEquals(robot1, World.getRobot("homer"));
        assertEquals(robot2, World.getRobot("marge"));
        World.remove(robot1);
        World.remove(robot2);
    }

    @Test
    public void getRobots() {
        Robot robot = new Robot("homer","machine");
        World.add(robot);
        assertEquals(robot, World.getRobots().values().toArray()[0]);
        World.remove(robot);
    }

    @Test
    public void removeRobot() {
        Robot robot1 = new Robot("homer","tank");
        Robot robot2 = new Robot("marge","sniper");
        World.add(robot1);
        World.add(robot2);
        World.remove(robot1);
        assertEquals(robot2, World.getRobots().values().toArray()[0]);
        World.remove("marge");
        assertEquals(0, World.getRobots().values().size());
    }

    @Test
    public void pathBlockedMiss() {
        Robot robot = new Robot("homer","sniper");
        World.add(robot);
        robot.setPosition(new Position(0,0));
        assertEquals("miss", World.pathBlocked(robot, new Position(0,5)));
        World.remove(robot);
    }

    @Test
    public void pathBlockedHitObjectTop() {
        Robot robot = new Robot("homer","machine");
        World.add(robot);
        robot.setDirection(180);
        robot.setPosition(new Position(0,0));
        assertEquals("hit obstacle 0 -1", World.pathBlocked(robot, new Position(0,-1)));
        World.remove(robot);
    }

    @Test
    public void pathBlockedHitObjectLeft() {
        Robot robot = new Robot("homer","sniper");
        World.add(robot);
        robot.setDirection(90);
        robot.setPosition(new Position(-1,-1));
        assertEquals("hit obstacle 0 -1", World.pathBlocked(robot, new Position(0,-1)));
        World.remove(robot);
    }

    @Test
    public void pathBlockedHitObjectBottom() {
        Robot robot = new Robot("homer","tank");
        World.add(robot);
        robot.setPosition(new Position(0,-4));
        assertEquals("hit obstacle 0 -3", World.pathBlocked(robot, new Position(0,-3)));
        World.remove(robot);
    }

    @Test
    public void pathBlockedHitObjectRight() {
        Robot robot = new Robot( "homer","tank");
        World.add(robot);
        robot.setDirection(270);
        robot.setPosition(new Position(4,-3));
        assertEquals("hit obstacle 3 -3", World.pathBlocked(robot, new Position(3,-3)));
        World.remove(robot);
    }

    @Test
    public void pathBlockedHitEnemyTop() {
        Robot robot = new Robot( "homer","sniper");
        Robot enemy = new Robot("flanders","machine");
        World.add(robot);
        World.add(enemy);
        robot.setDirection(180);
        World.setRobotPosition(robot, new Position(0,1));
        World.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", World.pathBlocked(robot, new Position(0,0)));
        World.remove(robot);
        World.remove(enemy);
    }

    @Test
    public void pathBlockedHitEnemyBottom() {
        Robot robot = new Robot("homer","machine");
        Robot enemy = new Robot("flanders","sniper");
        World.add(robot);
        World.add(enemy);
        World.setRobotPosition(robot, new Position(0,0));
        World.setRobotPosition(enemy, new Position(0,1));
        assertEquals("hit enemy flanders", World.pathBlocked(robot, new Position(0,1)));
        World.remove(robot);
        World.remove(enemy);
    }

    @Test
    public void pathBlockedHitEnemyLeft() {
        Robot robot = new Robot("homer","bomber");
        Robot enemy = new Robot("flanders","sniper");
        World.add(robot);
        World.add(enemy);
        robot.setDirection(90);
        World.setRobotPosition(robot, new Position(-1,0));
        World.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", World.pathBlocked(robot, new Position(0,0)));
        World.remove(robot);
        World.remove(enemy);
    }

    @Test
    public void pathBlockedHitEnemyRight() {
        Robot robot = new Robot("homer","machine");
        Robot enemy = new Robot("flanders","tank");
        World.add(robot);
        World.add(enemy);
        robot.setDirection(270);
        World.setRobotPosition(robot, new Position(1,0));
        World.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", World.pathBlocked(robot, new Position(0,0)));
        World.remove(robot);
        World.remove(enemy);
    }
}
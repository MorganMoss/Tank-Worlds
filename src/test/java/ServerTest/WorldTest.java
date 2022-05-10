package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Robot;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldTest {
    @Test
    public void addRobot() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot1 = new BasicRobot(world, "homer");
        Robot robot2 = new BasicRobot(world, "marge");
        world.add(robot1);
        world.add(robot2);
        assertEquals(robot1, world.getRobot("homer"));
        assertEquals(robot2, world.getRobot("marge"));
    }

    @Test
    public void getRobots() {
        World world = new World(new BasicMap(new Position(100, 100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        assertEquals(robot, world.getRobots().values().toArray()[0]);
    }

    @Test
    public void removeRobot() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot1 = new BasicRobot(world, "homer");
        Robot robot2 = new BasicRobot(world, "marge");
        world.add(robot1);
        world.add(robot2);
        world.remove(robot1);
        assertEquals(robot2, world.getRobots().values().toArray()[0]);
        world.remove("marge");
        assertEquals(0, world.getRobots().values().size());
    }

    @Test
    public void pathBlockedMiss() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        robot.setPosition(new Position(0,0));
        assertEquals("miss", world.pathBlocked(robot, new Position(0,5)));
    }

    @Test
    public void pathBlockedHitObjectTop() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        robot.setDirection(180);
        robot.setPosition(new Position(0,0));
        assertEquals("hit obstacle 0 -1", world.pathBlocked(robot, new Position(0,-1)));
    }

    @Test
    public void pathBlockedHitObjectLeft() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        robot.setDirection(90);
        robot.setPosition(new Position(-1,-1));
        assertEquals("hit obstacle 0 -1", world.pathBlocked(robot, new Position(0,-1)));
    }

    @Test
    public void pathBlockedHitObjectBottom() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        robot.setPosition(new Position(0,-4));
        assertEquals("hit obstacle 0 -3", world.pathBlocked(robot, new Position(0,-3)));
    }

    @Test
    public void pathBlockedHitObjectRight() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        world.add(robot);
        robot.setDirection(270);
        robot.setPosition(new Position(4,-3));
        assertEquals("hit obstacle 3 -3", world.pathBlocked(robot, new Position(3,-3)));
    }

    @Test
    public void pathBlockedHitEnemyTop() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        Robot enemy = new BasicRobot(world, "flanders");
        world.add(robot);
        world.add(enemy);
        robot.setDirection(180);
        world.setRobotPosition(robot, new Position(0,1));
        world.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", world.pathBlocked(robot, new Position(0,0)));
    }

    @Test
    public void pathBlockedHitEnemyBottom() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        Robot enemy = new BasicRobot(world, "flanders");
        world.add(robot);
        world.add(enemy);
        world.setRobotPosition(robot, new Position(0,0));
        world.setRobotPosition(enemy, new Position(0,1));
        assertEquals("hit enemy flanders", world.pathBlocked(robot, new Position(0,1)));
    }

    @Test
    public void pathBlockedHitEnemyLeft() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        Robot enemy = new BasicRobot(world, "flanders");
        world.add(robot);
        world.add(enemy);
        robot.setDirection(90);
        world.setRobotPosition(robot, new Position(-1,0));
        world.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", world.pathBlocked(robot, new Position(0,0)));
    }

    @Test
    public void pathBlockedHitEnemyRight() {
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "homer");
        Robot enemy = new BasicRobot(world, "flanders");
        world.add(robot);
        world.add(enemy);
        robot.setDirection(270);
        world.setRobotPosition(robot, new Position(1,0));
        world.setRobotPosition(enemy, new Position(0,0));
        assertEquals("hit enemy flanders", world.pathBlocked(robot, new Position(0,0)));
    }
}

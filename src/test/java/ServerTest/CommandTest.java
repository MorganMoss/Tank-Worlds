package ServerTest;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.command.*;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.shared.Robot;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    //setup is to modify the static world
    private static String oldMap;
    @BeforeEach
    public void changeConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            oldMap = properties.getProperty("map");
            properties.setProperty("map", "EmptyMap");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        World.resetMap();
    }

    @AfterAll
    static void revertConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty("map", oldMap);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @Test
    public void testRepairCommand(){
        Robot robot = new Robot("hal","sniper");
        World.add(robot);
        RepairCommand repairCommand = new RepairCommand(robot.getRobotName());
        assertEquals("Repair in progress",repairCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testLaunch(){
        Robot robot = new Robot("halk","sniper");
        LaunchCommand launch = new LaunchCommand(robot.getRobotName(),"testRobot");
        assertEquals("Success",launch.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testIdle(){
        Robot robot = new Robot("halk","sniper");
        World.add(robot);
        IdleCommand idleCommand = new IdleCommand(robot.getRobotName());
        assertEquals("idle",idleCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testReloadCommand(){
        Robot robot = new Robot("halk","sniper");
        World.add(robot);
        ReloadCommand reloadCommand = new ReloadCommand(robot.getRobotName());
        assertEquals("Reload in progress",reloadCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testFireCommandSuccess(){
        Robot robot = new Robot("peter","sniper");
        World.add(robot);
        World.setRobotPosition(robot, new Position(0,0));
        FireCommand fireCommand = new FireCommand(robot.getRobotName());
        assertEquals("Success",fireCommand.execute());
        World.remove(robot.getRobotName());
    }

    @Test
    public void testLeftCommand(){
        Robot robot = new Robot("roger","sniper");
        LeftCommand leftCommand = new LeftCommand(robot.getRobotName());
        World.add(robot);
        assertEquals("Success",leftCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testRightCommand(){
        Robot robot = new Robot("larry","sniper");
        RightCommand rightCommand = new RightCommand(robot.getRobotName());
        World.add(robot);
        assertEquals("Success",rightCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testBackCommand(){
        Robot robot = new Robot("moss","sniper");
        World.add(robot);
        World.setRobotPosition(robot, new Position(0,0));
        Command backCommand = new BackCommand(robot.getRobotName(),"5");
        assertEquals("Success",backCommand.execute());
        World.remove(robot.getRobotName());
    }
    @Test
    public void testForwardCommand(){
        Robot robot = new Robot("moss","sniper");
        ForwardCommand forwardCommand = new ForwardCommand(robot.getRobotName(),"5");
        World.add(robot);
        World.setRobotPosition(robot, new Position(0,0));
        assertEquals("Success",forwardCommand.execute());
        World.remove(robot.getRobotName());
    }


}

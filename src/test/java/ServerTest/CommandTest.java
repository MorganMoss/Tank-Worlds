package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.command.*;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.robot.BasicRobot;
import za.co.wethinkcode.robotworlds.server.robot.Robot;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    @Test
    public void testRepairCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "hal");
        world.add(robot);
        RepairCommand repairCommand = new RepairCommand(robot.getRobotName());
        assertEquals("Repair in progress",repairCommand.execute(world));
    }
    @Test
    public void testLaunch(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "halk");
        world.add(robot);
        LaunchCommand launch = new LaunchCommand(robot.getRobotName(),"BasicRobot");
        assertEquals("Success",launch.execute(world));
    }
    @Test
    public void testIdle(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "halk");
        world.add(robot);
        IdleCommand idleCommand = new IdleCommand(robot.getRobotName());
        assertEquals("idle",idleCommand.execute(world));
    }
    @Test
    public void testReloadCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "halk");
        world.add(robot);
        ReloadCommand reloadCommand = new ReloadCommand(robot.getRobotName());
        assertEquals("Reload in progress",reloadCommand.execute(world));
    }
    @Test
    public void testFireCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "peter");
        world.add(robot);
        FireCommand fireCommand = new FireCommand(robot.getRobotName());
        assertEquals("no_ammo",fireCommand.execute(world));
    }

    @Test
    public void testLeftCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "roger");
        LeftCommand leftCommand = new LeftCommand(robot.getRobotName());
        world.add(robot);
        assertEquals("Success",leftCommand.execute(world));
    }
    @Test
    public void testRightCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "larry");
        RightCommand rightCommand = new RightCommand(robot.getRobotName());
        world.add(robot);
        assertEquals("Success",rightCommand.execute(world));
    }
    @Test
    public void testBackCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "moss");
        world.add(robot);
        Command backCommand = new BackCommand(robot.getRobotName(),"5");
        assertEquals("Success",backCommand.execute(world));
    }
    @Test
    public void testForwardCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new BasicRobot(world, "moss");
        ForwardCommand forwardCommand = new ForwardCommand(robot.getRobotName(),"5");
        world.add(robot);
        assertEquals("Success",forwardCommand.execute(world));
    }


}

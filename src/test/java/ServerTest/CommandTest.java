package ServerTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.Position;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.server.command.*;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.Robot;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    @Test
    public void testRepairCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "hal","sniper");
        world.add(robot);
        RepairCommand repairCommand = new RepairCommand(robot.getRobotName());
        assertEquals("Repair in progress",repairCommand.execute(world));
    }
    @Test
    public void testLaunch(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "halk","sniper");
        world.add(robot);
        LaunchCommand launch = new LaunchCommand(robot.getRobotName(),"testRobot");
        assertEquals("Success",launch.execute(world));
    }
    @Test
    public void testIdle(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "halk","sniper");
        world.add(robot);
        IdleCommand idleCommand = new IdleCommand(robot.getRobotName());
        assertEquals("idle",idleCommand.execute(world));
    }
    @Test
    public void testReloadCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "halk","sniper");
        world.add(robot);
        ReloadCommand reloadCommand = new ReloadCommand(robot.getRobotName());
        assertEquals("Reload in progress",reloadCommand.execute(world));
    }
    @Test
    public void testFireCommandSuccess(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "peter","sniper");
        world.add(robot);
        FireCommand fireCommand = new FireCommand(robot.getRobotName());
        assertEquals("Success",fireCommand.execute(world));
    }



    @Test
    public void testLeftCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "roger","sniper");
        LeftCommand leftCommand = new LeftCommand(robot.getRobotName());
        world.add(robot);
        assertEquals("Success",leftCommand.execute(world));
    }
    @Test
    public void testRightCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "larry","sniper");
        RightCommand rightCommand = new RightCommand(robot.getRobotName());
        world.add(robot);
        assertEquals("Success",rightCommand.execute(world));
    }
    @Test
    public void testBackCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "moss","sniper");
        world.add(robot);
        Command backCommand = new BackCommand(robot.getRobotName(),"5");
        assertEquals("Success",backCommand.execute(world));
    }
    @Test
    public void testForwardCommand(){
        World world = new World(new BasicMap(new Position(100,100)));
        Robot robot = new Robot(world, "moss","sniper");
        ForwardCommand forwardCommand = new ForwardCommand(robot.getRobotName(),"5");
        world.add(robot);
        assertEquals("Success",forwardCommand.execute(world));
    }


}

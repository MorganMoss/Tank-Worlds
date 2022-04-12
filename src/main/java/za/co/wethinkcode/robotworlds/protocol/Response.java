package za.co.wethinkcode.robotworlds.protocol;

import java.util.ArrayList;
import java.util.HashMap;

import za.co.wethinkcode.robotworlds.server.robot.Robot;

public class Response {
    public ArrayList<Integer>[][] visibleWorldGrid;
    public Robot robot;
    public HashMap<Integer, Robot> surroundingRobots;

    //temp is for debugging; to be removed when world and robot are implemented
    public String temp;

    public Response(String temp){
        this.temp = temp;
    }

    public Response(ArrayList<Integer>[][] grid, Robot robot){
        //gets a grid for visuals
        //gets client's robot's information
        //gets surrounding robots in view's information
    }

    @Override
    public String toString() {
        return temp;
    }
}

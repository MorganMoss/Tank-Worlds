package za.co.wethinkcode.robotworlds.shared.protocols;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.shared.Robot;

import java.util.HashMap;

public class Response {
    /**
     * The client's robot with its current state
     */
    private final Robot robot;
    /**
     * The response given by the executed response
     */
    private final String commandResponse;
    /**
     * A view given by the world.look method.
     * It is a grid that represents what this robot can see
     */
    private final HashMap<Integer, HashMap<Integer, String>> map;
    /**
     * A list of nearby robots with their current states.
     */
    private final HashMap<String, Robot> enemyRobots;

    /**
     * The constructor for response
     * @param robot : the robot that gave the request for this response
     * @param commandResponse : the response to the command in the request given
     * @param map : an updated view on the world
     * @param enemyRobots : a list of the robots with the key being their identifier on the map
     */
    public Response(Robot robot, String commandResponse, HashMap<Integer, HashMap<Integer, String>> map, HashMap<String, Robot> enemyRobots) {
        this.robot = robot;
        this.commandResponse = commandResponse;
        this.map = map;
        this.enemyRobots = enemyRobots;
    }



    public HashMap<Integer, HashMap<Integer, String>> getMap() {
        return this.map;
    }

    public Robot getRobot() {
        return robot;
    }

    public String getCommandResponse() {
        return commandResponse;
    }

    public HashMap<String, Robot> getEnemyRobots() {
        return enemyRobots;
    }

    /**
     * this function uses Google Gson a java
     * data serialization package that takes an object
     * @return a string Json
     */
    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * this function uses Google Gson a java
     * Takes in a string Json and makes a response object
     * @param json : the string to be converted
     * @return a response object
     */
    public static Response deSerialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Response.class);
    }
}

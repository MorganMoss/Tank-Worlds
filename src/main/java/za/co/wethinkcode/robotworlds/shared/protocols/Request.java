package za.co.wethinkcode.robotworlds.shared.protocols;

import com.google.gson.Gson;

import java.util.List;

public class Request {
    /**
     * The name of the robot of the client that gave this request
     */
    private final String robotName;
    /**
     * A single word command that references one of the command child classes
     */
    private final String command;
    /**
     * Any arguments that are needed for the command
     */
    private final List<String> arguments;

    /**
     * command with arguments constructor
     * @param robotName : the name of the robot making this request
     * @param command : the name of the command
     * @param arguments : the arguments after the command name
     */
    public Request(String robotName, String command, List<String> arguments){
        this.robotName = robotName;
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * command with no arguments constructor
     * @param robotName : the name of the robot making this request
     * @param command : the name of the command
     */
    public Request(String robotName, String command) {
        this(robotName, command, null);
    }

    public String getRobotName() {
        return robotName;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    /**
     * this function uses Google Gson a java
     * data serialization package that takes an object
     * @return a string Json
     */
    public String serialize(){
        //initialize gson
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * this function uses Google Gson a java
     * Takes in a string Json and makes a request object
     * @param json : the string to be converted
     * @return a request object
     */
    public static Request deSerialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Request.class);
    }
}

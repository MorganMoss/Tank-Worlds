package za.co.wethinkcode.robotworlds.protocol;

import com.google.gson.Gson;

import java.util.List;

public class Request {
    // TODO: pull clientName from local global storage
    String clientName;
    String command = "idle";
    List<?> arguments;

    //default constructor
    public Request(){}

    //command and arguments constructor
    public Request(String name, String command, List<?> arguments){
        this.clientName = name;
        this.command = command;
        this.arguments = arguments;
    }

    //command and no arguments constructor
    public Request(String name, String command) {
        this.clientName = name;
        this.command = command;
    }

    /* serialize function
    * this function uses Google Gson a java
    * data serialization package that takes an object
    * and returns a string Json
    * */
    public String serialize(){
        //initialize gson
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

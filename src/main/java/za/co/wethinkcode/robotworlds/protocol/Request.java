package za.co.wethinkcode.robotworlds.protocol;

import java.util.ArrayList;
import java.util.List;

public class Request {
    String name;
    String command;
    List<String> arguments;

    public Request(String request) {
        this.name = "";
        this.command = "";
        this.arguments = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }
}

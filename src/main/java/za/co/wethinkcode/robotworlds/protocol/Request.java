package za.co.wethinkcode.robotworlds.protocol;

import java.util.ArrayList;
import java.util.List;

public class Request {
    public final String clientName;
    public final String commandName;
    public final List<String> commandArguments;

    public Request(String clientInput){
        //split into vars, validation of input happens here; 
        //i.e. the command name is valid and arguments valid
        // clientInput.split(" ");
        clientName = "";
        commandName = ""; //[0]
        commandArguments = new ArrayList<>(); //[The rest]
    }
}

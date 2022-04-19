package za.co.wethinkcode.robotworlds.protocol;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class Response {
    // TODO: pull clientName from server global storage
    String clientName;
    String result = "OK";

    public String getClientName(){return this.clientName;}

    /* TODO: create response DATA & STATE hashmap on server and getter for each
    String key1 = "value1";
    String key2 = "value2";
    HashMap<String, String> data = new HashMap<String, String>();
    public Response(String args){
        data.put("key1",key1);
        data.put("key2",key2);
    }*/


    //default constructor
    public Response(){}

    //string constructor
    public Response(String name, String request, String key1, String key2){
        this.clientName = name;
        this.result = request;
    }

    public Response(String name, String request) {
        this.clientName = name;
        this.result = request;
    }

    public static Response deSerialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Response.class);
    }

    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}

package za.co.wethinkcode.robotworlds.protocol;

import com.google.gson.Gson;

import java.util.HashMap;

public class Response {
    // TODO: pull clientName from server global storage

    private final String robotName;
    private final String result;
    private final HashMap<Integer, HashMap<Integer, Character>> map;

    /* TODO: create response DATA & STATE hashmap on server and getter for each */

    public Response(String name, String request, HashMap<Integer, HashMap<Integer, Character>> map) {
        this.robotName = name;
        this.result = request;
        this.map = map;
        //hardcoded response
//        this.map = new HashMap<>();
//        for (int x = 0; x < 10; x++) {
//            HashMap<Integer, Character> row = new HashMap<>();
//            for (int y = 0; y < 10; y++) {
//                row.putIfAbsent(y, ' '); //open space
//            }
//            this.map.putIfAbsent(x, row);
//        }
//        this.map.get(5).put(5, 'P'); //this player
//        this.map.get(7).put(6, 'X'); //obstacle
//        this.map.get(3).put(5, '1'); //enemy
//        this.map.get(3).put(1, '2'); //enemy
    }

    public static Response deSerialize(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Response.class);
    }

    public HashMap<Integer, HashMap<Integer, Character>> getMap() {
        return this.map;
    }

    public String getRobotName() {
        return robotName;
    }

    public String getResult() {
        return result;
    }

    public String serialize(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}

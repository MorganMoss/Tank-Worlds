package ServerTest;

import org.junit.jupiter.api.*;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    //setup is to modify the static world
    private static String oldMap;
    @BeforeAll
    static void changeConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            oldMap = properties.getProperty("map");
            properties.setProperty("map", "BasicMap");
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
            properties.store(fileOutputStream, "Test config override");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        World.resetMap();
        showWorld();
    }

    @AfterAll
    static void revertConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty("map", oldMap);
            fileInputStream.close();
            FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
            properties.store(fileOutputStream, "Test config override");
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    static void showWorld(){
        HashMap<Integer, HashMap<Integer, String>> map = World.look(new Position(0,0),
                (World.getMapSize().getX() > World.getMapSize().getY()) ?
                        (World.getMapSize().getX()/2+2): World.getMapSize().getY()/2+2);
        for (int y = map.get(0).size()-1; y >= 0;  y--){
            for (int x =0; x < map.size(); x++){
                try{
                    System.out.print("" + map.get(x).get(y).charAt(0) /*+ map.get(x).get(y).charAt(0)*/);
                } catch (NullPointerException odd) {
                    System.out.println(x + "," + y);
                }
            }
            System.out.print('\n');
        }
    }

    @Test
    public void testBasicMap(){
        Map basicMap = new BasicMap(new Position(2,3));
        assertEquals(2,basicMap.getMapSize().getX());
        assertEquals(3,basicMap.getMapSize().getY());
        assertEquals("square",basicMap.getObstacles().get(0).getShape());
        assertFalse(basicMap.getObstacles().get(0).isPositionBlocked(new Position(7, 13)));
    }
}

package ServerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.server.World;
import za.co.wethinkcode.robotworlds.shared.Position;
import za.co.wethinkcode.robotworlds.server.map.BasicMap;
import za.co.wethinkcode.robotworlds.server.map.Map;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
    //setup is to modify the static world
    private String oldMap;
    @BeforeEach
    public void changeConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            oldMap = properties.getProperty("map");
            properties.setProperty("map", "BasicMap");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
        }
        World.resetMap();
    }

    @AfterEach
    public void revertConfig(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty("map", oldMap);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error");
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

package za.co.wethinkcode.robotworlds.server;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFile  {
    int repair;
    int reload;
    int visibility;
    int shield;

    public ConfigFile() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/za/co/wethinkcode/robotworlds/server/config.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        this.repair = Integer.parseInt(properties.getProperty("repair"));
        this.reload = Integer.parseInt(properties.getProperty("reload"));
        this.shield = Integer.parseInt(properties.getProperty("shield"));
        this.visibility = Integer.parseInt(properties.getProperty("visibility"));
    }

}
